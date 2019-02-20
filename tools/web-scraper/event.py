import xml.etree.ElementTree as ET

class Event:
    """Stores the information about a specific event"""

    def __init__(self, date, location, title, duration):
        self.date = date
        self.location = location
        self.title = title
        self.duration = duration

    def to_xml(self):
        """Returns an XML tag containing info about the event"""

        return ET.Element('event', {
            'date': self.date.strftime('%Y-%m-%dT%H:%M:%S'),
            'location': self.location,
            'title': self.title,
            'duration': str(self.duration)
        })
