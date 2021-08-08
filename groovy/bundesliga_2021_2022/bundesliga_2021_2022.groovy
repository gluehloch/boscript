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


def bl_spieltag_2021_08_14 = service.findRound(bundesliga, 0)
if (bl_spieltag_2021_08_14.isPresent()) {
    bl_spieltag_2021_08_14 = bl_spieltag_2021_08_14.get()
} else {
    bl_spieltag_2021_08_14 = service.addRound(bundesliga, '2021-08-14 15:30:00', erste_bundesliga_groupe_type)
}
println "Runde $bl_spieltag_2021_08_14.dateTime"

// 1. Spieltag

service.addMatch(bl_spieltag_2021_08_14, '2021-08-13 20:30:00', bundesliga2020group, gladbach, bayernMuenchen)

/*
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

