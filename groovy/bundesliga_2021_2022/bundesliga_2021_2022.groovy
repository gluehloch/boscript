// groovy -c UTF-8 wm2018.groovy
// Copy the current JAR to the grape repository:
// cp ./target/betoffice-storage-2.6.0-SNAPSHOT.jar \
//   /cygdrive/c/Users/winkler/.groovy/grapes/de.winkler.betoffice/betoffice-storage/jars/betoffice-storage-2.6.0-SNAPSHOT.jar

@GrabResolver(name='gluehloch', root='http://maven.gluehloch.de/repository')
@Grab(group='org.slf4j', module='slf4j-api', version='1.6.1')

@Grab(group='org.apache.commons', module='commons-pool2', version='2.8.1')
@Grab(group='org.apache.commons', module='commons-dbcp2', version='2.7.0')
@Grab(group='org.mariadb.jdbc', module='mariadb-java-client', version='2.6.2')

@Grab(group='de.winkler.betoffice', module='betoffice-storage', version='2.8.0-SNAP-2021-05-31')

import org.springframework.context.support.ClassPathXmlApplicationContext

import java.time.*;
import java.time.format.*;

import de.winkler.betoffice.storage.*
import de.winkler.betoffice.storage.enums.*

import Service;

def validate(object) {
    if (object == null) {
        throw new NullPointerException();
    } else {
        println "OK: " + object
    }
}

def printTeams(group) {
    println "Teams $group"
    for (team in group.teams) {
        println team
    }
}

Service service = new Service();

def bundesliga
def seasonOptional = service.seasonService.findSeasonByName('Bundesliga', '2021/2022')
if (seasonOptional.isPresent()) {
    bundesliga = seasonOptional.get()
} else {
    bundesliga = new Season();
    bundesliga.name = 'Bundesliga'
    bundesliga.year = '2021/2022'
    bundesliga.mode = SeasonType.LEAGUE
    bundesliga.teamType = TeamType.DFB
    bundesliga = service.createSeason(bundesliga);
}

println bundesliga.name + " - " + bundesliga.year

def koeln = service.findTeam('1.FC Köln')
def unionBerlin = service.findTeam('Union Berlin')
def mainz = service.findTeam('FSV Mainz 05')	
def hoffenheom = service.findTeam('1899 Hoffenheim')
def bielefeld = service.findTeam('Arminia Bielefeld')
def leverkusen = service.findTeam('Bayer 04 Leverkusen')
def bayernMuenchen = service.findTeam('FC Bayern München')

Bor. Mönchengladbach
Borussia Dortmund
Eintracht Frankfurt
FC Augsburg
Hertha BSC
RB Leipzig
SC Freiburg
SpVgg Greuther Fürth
VfB Stuttgart
VfL Bochum
VfL Wolfsburg


newTeam = service.findTeam('Nordmazedonien')
if (!newTeam.present) {
    def team = new Team()
    team.name = 'Nordmazedonien'
    team.longName = 'Nordmazedonien'
    team.shortName = 'Nordmazedonien'
    team.xshortName = 'MKD'
    team.logo = 'nordmazedonien.gif'
    team.teamType = TeamType.FIFA
    service.updateTeam(team)
}

newTeam = service.findTeam('Schottland')
if (!newTeam.present) {
    def team = new Team()
    team.name = 'Schottland'
    team.longName = 'Schottland'
    team.shortName = 'Schottland'
    team.xshortName = 'SCO'
    team.logo = 'schottland.gif'
    team.teamType = TeamType.FIFA
    service.updateTeam(team)
}

 // def bundesliga = master.findGroupType('1. Bundesliga');
def gruppeA = service.findGroupType('Gruppe A');
validate gruppeA
def gruppeB = service.findGroupType('Gruppe B');
validate gruppeB
def gruppeC = service.findGroupType('Gruppe C');
validate gruppeC
def gruppeD = service.findGroupType('Gruppe D');
validate gruppeD
def gruppeE = service.findGroupType('Gruppe E');
validate gruppeE
def gruppeF = service.findGroupType('Gruppe F');
validate gruppeF

def achtelfinale = service.findGroupType('Achtelfinale');
validate achtelfinale
def viertelfinale = service.findGroupType('Viertelfinale');
validate viertelfinale
def halbfinale = service.findGroupType('Halbfinale');
validate halbfinale
def finale = service.findGroupType('Finale');
validate finale
/*
def platz3 = service.findGroupType('Spiel um Platz 3');
validate platz3
*/

// Gruppe A
def italien = service.findTeam('Italien').get()
validate italien

def schweiz = service.findTeam('Schweiz').get();
validate schweiz

def tuerkei = service.findTeam("Türkei").get()
validate tuerkei

def wales = service.findTeam('Wales').get();
validate wales

// Gruppe B
def belgien = service.findTeam('Belgien').get();
validate belgien

def daenemark = service.findTeam('Dänemark').get();
validate daenemark

def finnland = service.findTeam('Finnland').get();
validate finnland

def russland = service.findTeam('Russland').get();
validate russland

// Gruppe C
def niederlande = service.findTeam('Niederlande').get();
validate niederlande

def nordmazedonien = service.findTeam('Nordmazedonien').get();
validate nordmazedonien

def oesterreich = service.findTeam('Österreich').get()
validate oesterreich

def ukraine = service.findTeam('Ukraine').get();
validate ukraine

// Gruppe D
def england = service.findTeam('England').get();
validate england

def kroatien = service.findTeam('Kroatien').get();
validate kroatien

def schottland = service.findTeam('Schottland').get();
validate schottland

def tschechien = service.findTeam('Tschechien').get();
validate tschechien

// Gruppe E
def polen = service.findTeam('Polen').get();
validate polen

def schweden = service.findTeam('Schweden').get();
validate schweden

def slowakei = service.findTeam('Slowakei').get();
validate slowakei

def spanien = service.findTeam('Spanien').get();
validate spanien

// Gruppe F
def deutschland = service.findTeam('Deutschland').get();
validate deutschland

def frankreich = service.findTeam('Frankreich').get();
validate frankreich

def portugal = service.findTeam('Portugal').get();
validate portugal

def ungarn = service.findTeam('Ungarn').get()
validate ungarn


// TODO #addGroup liefert nicht die Group zurück, sondern die Saison.
/*
def em2021_gruppe_A = service.addGroup em2021, gruppeA
println "Gruppe A: $em2021_gruppe_A.id"

def em2021_gruppe_B = service.addGroup em2021, gruppeB
println "Gruppe B: $em2021_gruppe_B.id"

def em2021_gruppe_C = service.addGroup em2021, gruppeC
println "Gruppe C: $em2021_gruppe_C.id"

def em2021_gruppe_D = service.addGroup em2021, gruppeD
println "Gruppe D: $em2021_gruppe_D.id"

def em2021_gruppe_E = service.addGroup em2021, gruppeE
println "Gruppe E: $em2021_gruppe_E.id"

def em2021_gruppe_F = service.addGroup em2021, gruppeF
println "Gruppe F: $em2021_gruppe_F.id"
*/

def em2021_gruppe_achtelfinale = service.addGroup em2021, achtelfinale
println "Achtelfinale: $em2021_gruppe_achtelfinale.id"

def em2021_gruppe_viertelfinale = service.addGroup em2021, viertelfinale
println "Viertelfinale: $em2021_gruppe_viertelfinale.id"

def em2021_gruppe_halbfinale = service.addGroup em2021, halbfinale
println "Halbfinale: $em2021_gruppe_halbfinale.id"

def em2021_gruppe_finale = service.addGroup em2021, finale
println "Finale: $em2021_gruppe_finale.id"

/*
em2021_gruppe_A = service.addTeams(em2021, gruppeA, [italien, schweiz, tuerkei, wales])
printTeams(em2021_gruppe_A)

em2021_gruppe_B = service.addTeams(em2021, gruppeB, [belgien, daenemark, finnland, russland]);
printTeams(em2021_gruppe_B)

em2021_gruppe_C = service.addTeams(em2021, gruppeC, [niederlande, nordmazedonien, oesterreich, ukraine])
printTeams(em2021_gruppe_C)

em2021_gruppe_D = service.addTeams(em2021, gruppeD, [england, kroatien, schottland, tschechien])
printTeams(em2021_gruppe_D)

em2021_gruppe_E = service.addTeams(em2021, gruppeE, [polen, schweden, slowakei, spanien])
printTeams(em2021_gruppe_E)

em2021_gruppe_F = service.addTeams(em2021, gruppeF, [deutschland, frankreich, portugal, ungarn])
printTeams(em2021_gruppe_F)
*/ 

// Vorrunde
def round_2021_06_11 = service.findRound(em2021, 0)
if (round_2021_06_11.isPresent()) {
    round_2021_06_11 = round_2021_06_11.get()
} else {
    round_2021_06_11 = service.addRound(em2021, '2021-06-11 21:00:00', gruppeA)
}
println "Runde $round_2021_06_11.dateTime"

def round_2021_06_16 = service.findRound(em2021, 1)
if (round_2021_06_16.isPresent()) {
    round_2021_06_16 = round_2021_06_16.get()
} else {
    round_2021_06_16 = service.addRound(em2021, '2021-06-16 14:00:00', gruppeA)
}
println "Runde $round_2021_06_16.dateTime"

def round_2021_06_20 = service.findRound(em2021, 2)
if (round_2021_06_20.isPresent()) {
    round_2021_06_20 = round_2021_06_20.get()
} else {
    round_2021_06_20 = service.addRound(em2021, '2021-06-20 18:00:00', gruppeA)
}
println "Runde $round_2021_06_20.dateTime"


// Achtelfinale
def round_2021_06_26 = service.findRound(em2021, 3)
if (round_2021_06_26.isPresent()) {
    round_2021_06_26 = round_2021_06_26.get()
} else {
    round_2021_06_26 = service.addRound(em2021, '2021-06-26 18:00:00', achtelfinale)
}
println "Runde $round_2021_06_26.dateTime"

// 1. Spieltag
/*
service.addMatch(round_2021_06_11, '2021-06-11 21:00:00', em2021_gruppe_A, tuerkei, italien)

service.addMatch(round_2021_06_11, '2021-06-12 15:00:00', em2021_gruppe_A, wales, schweiz)
service.addMatch(round_2021_06_11, '2021-06-12 18:00:00', em2021_gruppe_B, daenemark, finnland)
service.addMatch(round_2021_06_11, '2021-06-12 21:00:00', em2021_gruppe_B, belgien, russland)

service.addMatch(round_2021_06_11, '2021-06-13 15:00:00', em2021_gruppe_D, england, kroatien)
service.addMatch(round_2021_06_11, '2021-06-13 18:00:00', em2021_gruppe_C, oesterreich, nordmazedonien)
service.addMatch(round_2021_06_11, '2021-06-13 21:00:00', em2021_gruppe_C, niederlande, ukraine)

service.addMatch(round_2021_06_11, '2021-06-14 15:00:00', em2021_gruppe_D, schottland, tschechien)
service.addMatch(round_2021_06_11, '2021-06-14 18:00:00', em2021_gruppe_E, polen, slowakei)
service.addMatch(round_2021_06_11, '2021-06-14 21:00:00', em2021_gruppe_E, spanien, schweden)

service.addMatch(round_2021_06_11, '2021-06-15 18:00:00', em2021_gruppe_F, ungarn, portugal)
service.addMatch(round_2021_06_11, '2021-06-15 21:00:00', em2021_gruppe_F, frankreich, deutschland)
*/

// 2. Spieltag
/*
service.addMatch(round_2021_06_16, '2021-06-16 15:00:00', em2021_gruppe_B, finnland, russland)
service.addMatch(round_2021_06_16, '2021-06-16 18:00:00', em2021_gruppe_A, tuerkei, wales)
service.addMatch(round_2021_06_16, '2021-06-16 21:00:00', em2021_gruppe_A, italien, schweiz)

service.addMatch(round_2021_06_16, '2021-06-17 15:00:00', em2021_gruppe_C, ukraine, nordmazedonien)
service.addMatch(round_2021_06_16, '2021-06-17 18:00:00', em2021_gruppe_B, daenemark, belgien)
// TODO Fehler: service.addMatch(round_2021_06_16, '2021-06-17 21:00:00', em2021_gruppe_C, italien, schweiz)
// TODO Korrektur: /Die Mannschafts-IDs austauschen/
service.addMatch(round_2021_06_16, '2021-06-17 21:00:00', em2021_gruppe_C, niederlande, oesterreich)

service.addMatch(round_2021_06_16, '2021-06-18 15:00:00', em2021_gruppe_E, schweden, slowakei)
service.addMatch(round_2021_06_16, '2021-06-18 18:00:00', em2021_gruppe_D, kroatien, tschechien)
service.addMatch(round_2021_06_16, '2021-06-18 21:00:00', em2021_gruppe_D, england, schottland)

service.addMatch(round_2021_06_16, '2021-06-19 15:00:00', em2021_gruppe_F, ungarn, frankreich)
service.addMatch(round_2021_06_16, '2021-06-19 18:00:00', em2021_gruppe_F, portugal, deutschland)
service.addMatch(round_2021_06_16, '2021-06-19 21:00:00', em2021_gruppe_E, spanien, polen)
*/

// 3. Spieltag
/*
service.addMatch(round_2021_06_20, '2021-06-20 18:00:00', em2021_gruppe_A, italien, wales)
service.addMatch(round_2021_06_20, '2021-06-20 18:00:00', em2021_gruppe_A, schweiz, tuerkei)

service.addMatch(round_2021_06_20, '2021-06-21 18:00:00', em2021_gruppe_C, nordmazedonien, niederlande)
service.addMatch(round_2021_06_20, '2021-06-21 18:00:00', em2021_gruppe_C, ukraine, oesterreich)
service.addMatch(round_2021_06_20, '2021-06-21 21:00:00', em2021_gruppe_B, finnland, belgien)
service.addMatch(round_2021_06_20, '2021-06-21 21:00:00', em2021_gruppe_B, russland, daenemark)

service.addMatch(round_2021_06_20, '2021-06-22 21:00:00', em2021_gruppe_D, tschechien, england)
service.addMatch(round_2021_06_20, '2021-06-22 21:00:00', em2021_gruppe_D, kroatien, schottland)

service.addMatch(round_2021_06_20, '2021-06-23 18:00:00', em2021_gruppe_E, slowakei, spanien)
service.addMatch(round_2021_06_20, '2021-06-23 18:00:00', em2021_gruppe_E, schweden, polen)
service.addMatch(round_2021_06_20, '2021-06-23 21:00:00', em2021_gruppe_F, deutschland, ungarn)
service.addMatch(round_2021_06_20, '2021-06-23 21:00:00', em2021_gruppe_F, portugal, frankreich)
*/

def em2021_achtelfinale = service.addTeams(em2021, achtelfinale, [
    wales, daenemark, italien, oesterreich, niederlande, tschechien, belgien, portugal,
    kroatien, spanien, frankreich, schweiz, deutschland, england, schweden, ukraine
])
printTeams(em2021_achtelfinale)

// 4. Spieltag Achtelfinale
/*
service.addMatch(round_2021_06_26, '2021-06-26 18:00:00', em2021_gruppe_achtelfinale, wales, daenemark)
service.addMatch(round_2021_06_26, '2021-06-26 21:00:00', em2021_gruppe_achtelfinale, italien, oesterreich)

service.addMatch(round_2021_06_26, '2021-06-27 18:00:00', em2021_gruppe_achtelfinale, niederlande, tschechien)
service.addMatch(round_2021_06_26, '2021-06-27 21:00:00', em2021_gruppe_achtelfinale, belgien, portugal)

service.addMatch(round_2021_06_26, '2021-06-28 18:00:00', em2021_gruppe_achtelfinale, kroatien, spanien)
service.addMatch(round_2021_06_26, '2021-06-28 21:00:00', em2021_gruppe_achtelfinale, frankreich, schweiz)

service.addMatch(round_2021_06_26, '2021-06-29 18:00:00', em2021_gruppe_achtelfinale, england, deutschland)
service.addMatch(round_2021_06_26, '2021-06-29 21:00:00', em2021_gruppe_achtelfinale, schweden, ukraine)
*/

// Viertelfinale
def round_2021_07_02 = service.findRound(em2021, 4)
if (round_2021_07_02.isPresent()) {
    round_2021_07_02 = round_2021_07_02.get()
} else {
    round_2021_07_02 = service.addRound(em2021, '2021-07-02 18:00:00', viertelfinale)
}
println "Runde $round_2021_07_02.dateTime"


def em2021_viertelfinale = service.addTeams(em2021, viertelfinale, [
    daenemark, italien, tschechien, belgien, spanien, schweiz, ukraine, england
])
println em2021_viertelfinale.class
printTeams(em2021_viertelfinale)

// 5. Spieltag / Achtelfinale
service.addMatch(round_2021_07_02, '2021-07-02 18:00:00', em2021_gruppe_viertelfinale, schweiz, spanien)
service.addMatch(round_2021_07_02, '2021-07-02 21:00:00', em2021_gruppe_viertelfinale, belgien, italien)

service.addMatch(round_2021_07_02, '2021-07-03 18:00:00', em2021_gruppe_viertelfinale, tschechien, daenemark)
service.addMatch(round_2021_07_02, '2021-07-03 21:00:00', em2021_gruppe_viertelfinale, ukraine, england)
