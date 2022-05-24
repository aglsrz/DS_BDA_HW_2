from datetime import timedelta, datetime
import json, random

SIZE = 5000

START_TIME = datetime.strptime('01/05/22 01:00:00', '%d/%m/%y %H:%M:%S')
END_TIME = datetime.strptime('01/05/22 06:00:00', '%d/%m/%y %H:%M:%S')

def random_date(start, end):
    delta = end - start
    int_delta = (delta.days * 24 * 60 * 60) + delta.seconds
    random_second = random.randrange(int_delta)
    random_date = start + timedelta(seconds=random_second)
    return random_date.strftime("%Y-%m-%dT%H:%M:%S")


if __name__ == '__main__':

    dataset = []

    with open('airports_dict.json') as json_file:
        airports_data = json.load(json_file)

    for i in range(SIZE):
        rnd_id_1 = random.randint(0, len(airports_data) - 1)
        rnd_id_2 = random.randint(0, len(airports_data) - 1)
        number = random.randrange(1000, 9999)
        deployTime = random_date(START_TIME, END_TIME)
        airportDeploy = airports_data[rnd_id_1]["airport"]
        airportArrive = airports_data[rnd_id_2]["airport"]
        record = {'number':number,'deployTime':deployTime, 'airportDeploy':airportDeploy, 'airportArrive':airportArrive}
        dataset.append(record)

    with open('flights_dataset.json', 'w') as outfile:
        outfile.write(json.dumps(dataset))
