import json, urllib.request, urllib.error, time, os
from pathlib import Path

BASE = "https://cloudtech-api-maquinas-yg55.onrender.com"
results = []
def req(method, path, expected, body=None, validate=None):
    data = None if body is None else json.dumps(body, ensure_ascii=False).encode("utf-8")
    request = urllib.request.Request(BASE+path, data=data, method=method, headers={"Content-Type":"application/json","Accept":"application/json, text/plain, */*"})
    start = time.monotonic()
    try:
        with urllib.request.urlopen(request, timeout=100) as response:
            code, raw = response.status, response.read().decode("utf-8")
    except urllib.error.HTTPError as response:
        code, raw = response.code, response.read().decode("utf-8")
    try: parsed = json.loads(raw)
    except ValueError: parsed = raw
    ok = code == expected and (validate(parsed) if validate else True)
    record = {"method":method,"url":BASE+path,"expected":expected,"actual":code,"request":body,"response":parsed,"seconds":round(time.monotonic()-start,3),"passed":bool(ok)}
    results.append(record)
    print(json.dumps(record,ensure_ascii=False),flush=True)
    if not ok: raise AssertionError(f"{method} {path}: expected {expected}, got {code}")
    return parsed

try:
    req("GET","/status",200,validate=lambda x: x["status"]=="online")
    req("GET","/",200,validate=lambda x: "API de Monitoramento" in x)
    req("GET","/versao",200,validate=lambda x: x=="1.0.0")
    req("GET","/maquinas",200,validate=lambda x: isinstance(x,list))
    sample={"nome":"CNC Demo CI "+os.getenv("GITHUB_RUN_ID","manual"),"tipo":"CNC","setor":"Usinagem","status":"em operação"}
    m=req("POST","/maquinas/cadastro",201,sample,lambda x: isinstance(x["id"],int))
    mid=m["id"]
    req("GET",f"/maquinas/{mid}",200,validate=lambda x:x["id"]==mid)
    req("PUT",f"/maquinas/{mid}",200,{**sample,"setor":"Produção"},lambda x:x["setor"]=="Produção")
    req("DELETE",f"/maquinas/{mid}",204)
    req("GET",f"/maquinas/{mid}",404)
    m=req("POST","/maquinas/cadastro",201,{**sample,"nome":sample["nome"]+" - histórico"})
    mid=m["id"]
    p=req("POST","/producao",201,{"maquinaId":mid,"consumoKw":12.5,"horarioAcionamento":"08:30","pecasProduzidas":150},lambda x:x["maquina"]["id"]==mid and x["pecasProduzidas"]==150)
    req("GET","/producao",200,validate=lambda x:any(item["id"]==p["id"] for item in x))
    req("DELETE",f"/maquinas/{mid}",409)
finally:
    Path("evidencias-api-publica.json").write_text(json.dumps(results,ensure_ascii=False,indent=2),encoding="utf-8")
print(f"{len(results)} verificações HTTP aprovadas. Registro de produção fictício mantido para demonstrar integridade referencial.")
