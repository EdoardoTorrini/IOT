from os import path
import json
import random

PATH, DATA = "accelleration.json", None
if path.exists(PATH):
    with open(PATH, "r+") as f:
        DATA = json.loads(f.read())

for elem in DATA:
    if isinstance(elem, dict):
        try:
            elem["code"] = "door.smart.lock.01"
            elem["is_open"] = False
            elem["lock"] = True

            n = random.uniform(5, 20)
            elem["accelleration"] = n

        except TypeError:
            print(f"SEI UN PIRLA")
            break

with open(PATH, "w+") as f:
    f.write(json.dumps(DATA))