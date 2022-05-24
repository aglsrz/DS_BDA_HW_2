#!/bin/bash

output_file="results.json"

usage(){
	echo "Pass no options to only get the result of computation
	--update-data - to load new data from airports_dict and flights_dataset; then compute and get result
	--compute - to compute and get result"
	exit
}

update_data(){
	#Send data to ignite cache
	curl -X POST http://localhost:8080/flight/add/dict -H 'Content-Type:application/json' --data-binary '@airports_dict.json'
	curl -X POST http://localhost:8080/flight/add/list -H 'Content-Type:application/json' --data-binary '@flights_dataset.json'
}

compute(){
	# compute in ingnite compute and get computation results
	echo "Sending request to flight/compute..."
	time curl -X GET http://localhost:8080/flight/compute
}

get_result(){
	echo "Sending request to flight/result.."
	curl -X GET http://localhost:8080/flight/result | jq | sed '/id/d' >  $output_file
	echo "See results in $output_file"
	head $output_file
}

if [ -n "$1" ]
    then
       case "$1" in
	--help) usage ;;
	--update-data) update_data; compute;;
	--compute) compute;;
	*) echo "No option $1 found"; usage;;
       esac
fi

get_result
