import xml.etree.ElementTree as ET
import sys

if len(sys.argv) < 2:
	print('arg1 = input session list path, arg2 = output map path')

sessionListPath = sys.argv[1]
mapPath = sys.argv[2]

sessions = ET.parse(sessionListPath)
root = sessions.getroot()

venues = list()

for child in root:
	if child.attrib['venue'] not in venues:
		venues.append(child.attrib['venue'])

rootMap = ET.Element('map')
for i in range(0, len(venues)):
	for j in range(i+1, len(venues)):
		edge = ET.SubElement(rootMap, 'edge')
		edge.set('from', venues[i])
		edge.set('to', venues[j])
		edge.set('travelTime', '0')

tree = ET.ElementTree(rootMap)
tree.write(mapPath)
