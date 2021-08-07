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

def koeln = service.findTeam('1.FC Köln').get()
validate koeln

def unionBerlin = service.findTeam('Union Berlin').get()
validate unionBerlin

def mainz = service.findTeam('FSV Mainz 05').get()
validate mainz

def hoffenheim = service.findTeam('1899 Hoffenheim').get()
validate hoffenheim

def bielefeld = service.findTeam('Arminia Bielefeld').get()
validate bielefeld

def leverkusen = service.findTeam('Bayer 04 Leverkusen').get()
validate leverkusen

def bayernMuenchen = service.findTeam('FC Bayern München').get()
validate bayernMuenchen

def gladbach = service.findTeam('Borussia MGladbach').get()
validate gladbach

def dortmund = service.findTeam('Borussia Dortmund').get()
validate dortmund

def frankfurt = service.findTeam('Eintracht Frankfurt').get()
validate frankfurt

def augsburg = service.findTeam('FC Augsburg').get()
validate augsburg

def hertha = service.findTeam('Hertha BSC Berlin').get()
validate hertha

def leipzig = service.findTeam('RB Leipzig').get()
validate leipzig

def freiburg = service.findTeam('SC Freiburg').get()
validate freiburg

def fuerth = service.findTeam('SpVgg Greuther Fürth').get()
validate fuerth

def stuttgart = service.findTeam('VfB Stuttgart').get()
validate stuttgart

def bochum = service.findTeam('VfL Bochum').get()
validate bochum

def wolfsburg = service.findTeam('VfL Wolfsburg').get()
validate wolfsburg

def erste_bundesliga_groupe_type = service.findGroupType('1. Liga');
validate erste_bundesliga_groupe_type


def bundesliga2021group = service.addGroup bundesliga, erste_bundesliga_groupe_type
bundesliga2021group = service.findGroup(bundesliga, erste_bundesliga_groupe_type)
println "Gruppe Bundesliga 2021/2022: $bundesliga2021group"

bundesliga2020group = service.addTeams(bundesliga, erste_bundesliga_groupe_type, [
    unionBerlin,
    mainz,
    hoffenheim,
    bielefeld,
    leverkusen,
    bayernMuenchen,
    gladbach,
    dortmund,
    frankfurt,
    augsburg,
    hertha,
    leipzig,
    freiburg,
    fuerth,
    stuttgart,
    bochum,
    wolfsburg
])



// Vorrunde
/*
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
*/

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
