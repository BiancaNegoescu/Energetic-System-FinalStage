import rounds.OtherRounds;
import rounds.RoundZero;
import fileinput.Input;
import fileinput.InputLoader;
import fileoutput.OutputFormat;

import fileoutput.MyWriter;
import main.Consumer;
import main.Copy;
import main.Distributor;
import main.Factory;

public class Main {

    /**
     * javadoc
     *
     * @param args
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {
        InputLoader inputLoader = new InputLoader(args[0]);
        Input input = inputLoader.readData();
        Factory factory = Factory.getInstance();
        Distributor d = Distributor.getInstance();
        Consumer c = Consumer.getInstance();
        for (int i = 0; i < input.getNumberofTurns() + 1; i++) {
            if (i == 0) {
                RoundZero roundZero = new RoundZero(input);
                roundZero.chooseProducer();
                roundZero.calculateProductionCost();
                roundZero.calculateContractPriceAll();
                roundZero.chooseDistributor();
                roundZero.calculateBudget();
                roundZero.deleteMonth();
            } else {
                OtherRounds otherRounds = new OtherRounds(input, i);
                otherRounds.update();
                otherRounds.updateContractPrices();
                otherRounds.eraseContract();
                otherRounds.chooseProducer();
                otherRounds.calculateProductionCost();
                otherRounds.chooseDistributor();
                otherRounds.calculateBudget();
                otherRounds.deleteMonth();
                otherRounds.deleteBankruptConsumers();
                otherRounds.consumersWithoutDistributor();
                otherRounds.updateProducers();
                otherRounds.chooseNewProducers();
                otherRounds.updateMonthlyStats();
            }
            MyWriter fileWriter = new MyWriter();
            Copy copy = Copy.getInstance();
            OutputFormat output = copy.copy(input.getConsumersData(), input.getDistributorsData(),
                    input.getProducersData(), input);
            fileWriter.writeFile(args[1], output);
        }
    }
}









