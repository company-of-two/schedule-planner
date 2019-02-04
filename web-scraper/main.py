from get import get_url
from schedule import Schedule

if __name__ == '__main__':

    url = 'https://program.lfs.cz/?&alldates=1'

    data = get_url(url)
    print(len(data))
    lfsSchedule = Schedule(data)
