from requests import get
from requests.exceptions import RequestException
from contextlib import closing

def get_url(url):
    try:
        with closing(get(url, stream=True)) as response:
            if is_good_response(response):
                return response.content
            else:
                return None

    except RequestException as e:
        print('Error occured while trying to get {0}: {1}'.format(url, str(e)))
        return None


def is_good_response(response):
    content_type = response.headers['Content-Type'].lower()

    return (
        response.status_code == 200
        and content_type is not None
        and content_type.find('html') != -1
    ) 
