from get import get_url
from programme import Programme

url = 'https://program.lfs.cz/?&alldates=1'

# Download the website at specified URL
data = get_url(url)

# Create the programme and export it
programme = Programme(data)
programme.export('../programmes/summer-movie-school-2018-programme.xml')
