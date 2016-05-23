#!/bin/bash
echo y|cf d patientDataService-blue -r
echo y|cf d patientDataService-green -r
cf ds patient-service -f
cf ds p-rabbitmq -f
cf ds p-mysql -f
