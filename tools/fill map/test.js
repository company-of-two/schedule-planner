let town = 'Uherské Hradiště';



function LoadTxtFile(p) {
  var AllTxtdata = '';
  var targetFile = p.target.files[0];
  if(targetFile){
    var readXml = null;
    var FileRead = new FileReader();
    FileRead.onload = function (e) {
      readXml=e.target.result;
      //console.log(readXml);
      var parser = new DOMParser();
      var doc = parser.parseFromString(readXml, "application/xml");
      //console.log(doc);
      var edges = doc.getElementsByTagName('edge');               

      var from = edges[0].attributes.getNamedItem('from').nodeValue + ' ' + city;
      var to = edges[0].attributes.getNamedItem('to').nodeValue + ' ' + city;


    }
    FileRead.readAsText(targetFile);
  }
}
document.getElementById('myFile').addEventListener('change', LoadTxtFile, false);

var inputEl = document.querySelector("input[type='text']");
var suggest = new SMap.Suggest(inputEl);
/* suggest.addListener("suggest", function(suggestData) {
  // vyber polozky z naseptavace
  alert(suggestData.phrase);
}).addListener("close", function() {
  console.log("suggest byl zavren/skryt");
}).addListener("request-items", function(items) {
  console.log('items length: ', items.length);
});
 */
 function getVenue(name) {
	return new Promise(function(resolve, reject) {
  	suggest.addListener("request-items", resolve);
    suggest.open();
		inputEl.value = name;
		suggest.send();
  })
}

function getRoute(coords) {
	return new Promise(function(resolve, reject) {
  	var route = new SMap.Route(coords, resolve, { criterion: "turist2" });
  })
}






var centerMap = SMap.Coords.fromWGS84(14.40, 50.08);
var m = new SMap(JAK.gel("m"), centerMap, 16);
var l = m.addDefaultLayer(SMap.DEF_BASE).enable();
m.addDefaultControls(); 

var nalezeno = function(route) {
    var vrstva = new SMap.Layer.Geometry();
    m.addLayer(vrstva).enable();

    var coords = route.getResults().geometry;
    var cz = m.computeCenterZoom(coords);
    m.setCenterZoom(cz[0], cz[1]);
    var g = new SMap.Geometry(SMap.GEOMETRY_POLYLINE, null, coords);
    vrstva.addGeometry(g);
    //
    console.log(route.getResults().time/60, "min");
}

////////////////////////////////////////////////////

async function fetchRouteAsync(venueNames) {
	var coords = [];
 	var items;
 
 	items = await getVenue(venueNames[0]);
  //console.log(items[0]);
  coords.push(SMap.Coords.fromWGS84(items[0].longitude, items[0].latitude));
  items = await getVenue(venueNames[1]);
  //console.log(items[0]);
  coords.push(SMap.Coords.fromWGS84(items[0].longitude, items[0].latitude));
  return await getRoute(coords);
}

/* var coords = [];

getVenue('kino hvězda uherské hradiště').then(function(items) {
  //console.log(items[0]);
  coords.push(SMap.Coords.fromWGS84(items[0].longitude, items[0].latitude));
  return getVenue('slovácké divadlo uherské hradiště');
}).then(function(items) {
  //console.log(items[0]);
  coords.push(SMap.Coords.fromWGS84(items[0].longitude, items[0].latitude));
  console.log(coords);
  return getRoute(coords);
}).then(function(route) {
  //console.log(route);
  //alert(route.getResults().time/60, "min");
  nalezeno(route);
}) */

/* var coords = [
    SMap.Coords.fromWGS84(14.434, 50.084),
    SMap.Coords.fromWGS84(16.600, 49.195)
]; */

/* suggest.open();
inputEl.value = 'kino hvězda uherské hradiště';
suggest.send(); */
//var route = new SMap.Route(coords, nalezeno, { criterion: "turist2" });

/* alert(Object.keys(route)); */

fetchRouteAsync(['kino hvězda uherské hradiště', 'slovácké divadlo uherské hradiště'])
.then(function(route) {
	nalezeno(route);
});
