package glacialExpedition.core;

import glacialExpedition.common.ConstantMessages;
import glacialExpedition.common.ExceptionMessages;
import glacialExpedition.models.explorers.AnimalExplorer;
import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.explorers.GlacierExplorer;
import glacialExpedition.models.explorers.NaturalExplorer;
import glacialExpedition.models.mission.Mission;
import glacialExpedition.models.mission.MissionImpl;
import glacialExpedition.models.states.State;
import glacialExpedition.models.states.StateImpl;
import glacialExpedition.repositories.ExplorerRepository;
import glacialExpedition.repositories.StateRepository;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static glacialExpedition.common.ConstantMessages.*;

public class ControllerImpl implements Controller {

    private ExplorerRepository explorerRepository;
    private StateRepository stateRepository;
    private int exploredStates;

      public ControllerImpl() {
        this.explorerRepository = new ExplorerRepository();
        this.stateRepository = new StateRepository();
    }

    @Override
    public String addExplorer(String type, String explorerName) {
        Explorer explorer;
        switch (type){
            case "AnimalExplorer":
                explorer = new AnimalExplorer(explorerName);
                break;
            case "GlacierExplorer":
                explorer = new GlacierExplorer(explorerName);
                break;
            case "NaturalExplorer":
                explorer = new NaturalExplorer(explorerName);
                break;
            default:
                throw new IllegalArgumentException(String.format(ExceptionMessages.EXPLORER_DOES_NOT_EXIST, explorerName));
        }

        this.explorerRepository.add(explorer);
        return String.format(ConstantMessages.EXPLORER_ADDED, type,explorerName);
    }

    @Override
    public String addState(String stateName, String... exhibits) {
        StateImpl state = new StateImpl(stateName);

        for (String exh : exhibits) {
            state.getExhibits().add(exh);
        }

        this.stateRepository.add(state);
        return String.format(STATE_ADDED, stateName);
    }

    @Override
    public String retireExplorer(String explorerName) {
        Explorer existingExplorer = this.explorerRepository.byName(explorerName);
        if(existingExplorer==null){
            throw new IllegalArgumentException(String.format(ExceptionMessages.EXPLORER_DOES_NOT_EXIST, explorerName));
        }
        this.explorerRepository.remove(existingExplorer);
        return String.format(ConstantMessages.EXPLORER_RETIRED,explorerName);
    }

    @Override
    public String exploreState(String stateName) {
        State state = this.stateRepository.byName(stateName);
        List<Explorer> suitableExplorers = this.explorerRepository
                .getCollection()
                .stream()
                .filter(explorer -> explorer.getEnergy()>50)
                .collect(Collectors.toList());

        if(suitableExplorers.isEmpty()){
            throw new IllegalArgumentException(ExceptionMessages.STATE_EXPLORERS_DOES_NOT_EXISTS);
        }

        Mission mission = new MissionImpl();
        mission.explore(state, suitableExplorers);

        long retiredExplorers = suitableExplorers.stream().filter(e->e.getEnergy()==0).count();
        this.exploredStates++;

        return String.format(STATE_EXPLORER, stateName, retiredExplorers);
    }

    @Override
    public String finalResult() {
        StringBuilder result = new StringBuilder();

        result.append(String.format(FINAL_STATE_EXPLORED, this.exploredStates))
                .append(System.lineSeparator());
        result.append(FINAL_EXPLORER_INFO).append(System.lineSeparator());

        this.explorerRepository
                .getCollection()
                .forEach(explorer -> result
                        .append(explorer.toString())
                        .append(System.lineSeparator()));

        return result.toString().trim();
    }
}
