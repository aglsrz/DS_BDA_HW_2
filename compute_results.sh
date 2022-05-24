#!/bin/bash

output_file="results.json"

#Send data to ignite cache
curl -X POST http://localhost:8080/flight/create/dict -H 'Content-Type:application/json' --data-binary '@airports_dict.json'
curl -X POST http://localhost:8080/flight/create/list -H 'Content-Type:application/json' --data-binary '@flights_dataset.json'
# compute in ingnite compute and get computation results
echo "Sending request to flight/compute..."
time curl -X GET http://localhost:8080/flight/compute
echo "Sending request to flight/result.."
curl -X GET http://localhost:8080/flight/result | jq | tee  $output_file
