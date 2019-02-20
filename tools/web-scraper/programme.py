from bs4 import BeautifulSoup
from datetime import datetime
from xml.dom import minidom
import xml.etree.ElementTree as ET
from event import Event
import constants as const

class Programme:
    """Represents the programme, stores all the events"""
    
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
                date_str = item.text.strip()
                date = datetime.strptime(date_str.split()[1], '%d.%m.%Y')

            # <a/> tag indicates new event 
            elif item.name == 'a':
                
                # Get the information about the event
                time = item.find('div', class_=const.TIME).text.strip().split(':')
                location = item.find('div', class_=const.LOCATION).text.strip()
                title = item.find('div', class_=const.TITLE).text.strip()
                description = item.find('div', class_=const.DESCRIPTION).text.strip()
                
                # Store the date and time together in a datetime object
                hours = int(time[0])
                minutes = int(time[1])
                event_date = date.replace(hour=hours, minute=minutes)
                
                duration = -1
                # Split the description by slash and search for the string containing
                # 'min.' substring, then add up all the numbers in this string
                for description_part in description.split('/'):
                    if const.MINUTES in description_part:
                        duration = sum([int(a) for a in description_part.split() if a.isdigit()])

                # Store the event
                self.events.append(Event(event_date, location, title, duration))

    def export(self, filename):
        """Exports the programme into a file with specified filename"""
        
        programme = ET.Element('programme')
        for event in self.events:
            programme.append(event.to_xml())

        output = ET.tostring(programme)
        pretty_output = minidom.parseString(output).toprettyxml()

        with open(filename, 'w', encoding='utf-8') as f:
            f.write(pretty_output)
