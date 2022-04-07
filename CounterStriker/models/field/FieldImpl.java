package CounterStriker.models.field;

import CounterStriker.common.OutputMessages;
import CounterStriker.models.players.CounterTerrorist;
import CounterStriker.models.players.Player;
import CounterStriker.models.players.Terrorist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FieldImpl implements Field {

    @Override
    public String start(Collection<Player> players){
        List<Player> terrorists = getPlayersByType(players, "Terrorist");
        List<Player> counterTerrorists = getPlayersByType(players, "CounterTerrorist");

        while(counterTerrorists.stream().anyMatch(Player::isAlive) && terrorists.stream().anyMatch(Player::isAlive)){
            terroristAttack(terrorists, counterTerrorists);
            counterTerrorists = counterTerrorists.stream().filter(Player::isAlive).collect(Collectors.toList());

            for (Player contraTerrorist : counterTerrorists) {
                for (Player terrorist : terrorists) {
                    terrorist.takeDamage(contraTerrorist.getGun().fire());
                }
            }
            terrorists = terrorists.stream().filter(Player::isAlive).collect(Collectors.toList());
        }

        return terrorists.stream().anyMatch(Player::isAlive) ? OutputMessages.TERRORIST_WINS : OutputMessages.COUNTER_TERRORIST_WINS;
    }

    private void terroristAttack(List<Player> terrorists, List<Player> counterTerrorists) {
        for (Player terrorist : terrorists) {
            for (Player contraTerrorist : counterTerrorists) {
                contraTerrorist.takeDamage(terrorist.getGun().fire());
            }
        }
    }

    private List<Player> getPlayersByType(Collection<Player> players, String type){
        return players
                .stream()
                .filter(player -> player
                        .getClass()
                        .getSimpleName()
                        .equals(type))
                .collect(Collectors.toList());
    }

}
