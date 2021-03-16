# Master - Architekturen moderner Informationssysteme

The goal of this master module was to implement two versions of the same Software and compare them. 
A monolithic implementation and a microservice implementation.

This is the microservice implementation, you can find the monolithic implementation [here](https://github.com/ixLikro/master-ami-java-contact-tracing-monolith).


# Contact tracking

This software tracks the movements of individual users,
create heat maps of movement hotspots and provide an interface for sending notifications of contacts with infected individuals.

The goal of this software was to implement a technical showcase rather than a useful real world application.  

![readme gif](https://github.com/ixLikro/master-ami-java-contact-tracing-services/blob/master/misc/readme_gif.gif?raw=true)
## Microservices overview

| name  | default <br /> port | type | description  |
|---|---|---|---|
|data-generator|8080| Quarkus | A helper service that is able to generate multiple users with multiple movements.   |
|analyse|8081| Quarkus| Provides the web pages with the processed data.  |
|data-acceptance| 8082|Quarkus| Handles the raw movements and the buffer database.  |
|preprocessing| 8083|Quarkus| Reads the buffer database and saves the aggregated data in the main database |
|landingpage|8084| Wildfly| The jsf page: landingpage  |
|visualization|8085| Wildfly| The jsf page: visualization  |
|trend|8086| Wildfly| The jsf page: trend  |
|contact-tracking|8087| Quarkus| tracks the actual movements |
|db-main|5432| PostgreSQL| the main database (nachverfolgung)  |
|db-buffer|5433 <- host <br /> 5432 <- container| PostgreSQL| the buffer database (movement_data)  |

From the host are all container available at http://localhost:{port number}
<br />
Other container can communicate over a podman network http://{service name}:{port number}

## Architecture overview
![Architecture overview](https://github.com/ixLikro/master-ami-java-contact-tracing-services/blob/master/misc/architecture.png?raw=true)

## Build services **
1. Clone this repo
   ```
   git clone https://gitlab-fi.ostfalia.de/ami-team-1/aim-team-1.git
   ```
2. cd into the cloned directory
   ```
   cd aim-team-1/
   ```
3. give execution privilege to the two scripts
   ```
   chmod u+x build-all.bash
   chmod u+x run-all.bash
   ```
4. build all images
   ```
   ./build-all.bash
   ```
   Take some caffe and enjoy the show, this takes a while. Five Quarkus uber jars, three wildfly bootable jars and two database images will be built.

# Run services **
if you have successfully built all services you can run them: 
```
./run-all.bash
```
This script stats all ten Services and opens the landingpage in firefox. 

<br />

** We are using [Podman](https://podman.io/), but docker should be working too. Just replace all podman occurrences with docker inside the .bash scripts.
 
## License overview of included 3rd party libraries

Leaflet<br/>
License: BSD<br/>
https://github.com/Leaflet/Leaflet/blob/master/LICENSE 

Leaflet.heat<br/>
License: BSD 2<br/>
https://github.com/Leaflet/Leaflet.heat/blob/gh-pages/LICENSE

Chart.js<br/>
License: MIT<br/>
https://github.com/chartjs/Chart.js/blob/master/LICENSE.md 

Geodaten<br/>
Geodatenzentrum ©GeoBasis-DE / BKG 2018 (VG250 31.12., Daten verändert)<br/>
https://gdz.bkg.bund.de/ 

Einwohnerzahlen<br/>
Statistisches Bundesamt, Wiesbaden 2019 - Gemeindeverzeichnis<br/>
https://www.destatis.de/DE/Themen/Laender-Regionen/Regionales/Gemeindeverzeichnis/_inhalt.html 
