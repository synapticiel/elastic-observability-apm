import csv
import json
import requests

api_url = 'http://localhost:8081/api/cars'


for row in csv.DictReader(open('./input-cars.csv')):
    data = {}

    for field in row:
        key, _, sub_key = field.partition('.')
        if not sub_key:
            data[key] = row[field]
        else:
            if key not in data:
                data[key] = [{}]
            data[key][0][sub_key] = row[field]

    print(json.dumps(data, indent=True))
    print('---------------------------')
    resp = requests.post(api_url, json=data)
    print (resp)
