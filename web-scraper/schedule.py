from bs4 import BeautifulSoup
from event import Event
import constants as const

class Schedule:
    """Represents the schedule, stores all the events"""
    
    def __init__(self, data):
        self.data = data
        self.events = []
        
        self.parseData(data)
        
    
    def parseData(self, data):
        """Parses received data for events and stores them for further processing"""

        soup = BeautifulSoup(data)

        date = None

        # Element which contains all the events
        programme = soup.find('div', class_=const.PROGRAMME)

        for item in programme.children:

            # <div/> tag indicates new date
            if item.name == 'div':
                date = item.text.strip()

            # <a/> tag indicates new event 
            elif item.name == 'a':
                
                # Get the information about the event
                time = item.find('div', class_=const.TIME).text.strip()
                cinema = item.find('div', class_=const.CINEMA).text.strip()
                title = item.find('div', class_=const.TITLE).text.strip()
                description = item.find('div', class_=const.DESCRIPTION).text.strip()
                
                duration = -1
                for description_part in description.split('/'):
                    if const.MINUTES in description_part:
                        duration = sum([int(a) for a in description_part.split() if a.isdigit()])

                # Store the event
                self.events.append(Event(date, time, cinema, title, duration))

    def exportSchedule(self, file_name):
        """Exports the schedule into a file with specified file name"""
        pass
