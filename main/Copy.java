package main;

import fileinput.ConsumerInputData;
import fileinput.DistributorInputData;
import fileinput.Input;
import fileoutput.ConsumerOutputData;
import fileoutput.DistributorOutputData;
import fileoutput.OutputFormat;
import fileinput.ProducersInputData;
import fileoutput.ProducerOutputData;

import java.util.List;

public final class Copy {

    private static Copy singleCopyInstance = null;

    private Copy() {
    }

    /**
     * se creaza un obiect al clasei atunci cand este nevoie de el (Lazy Initialisation)
     * @return instanta a clasei
     */
    public static Copy getInstance() {
        if (singleCopyInstance == null) {
            singleCopyInstance = new Copy();
        }
        return singleCopyInstance;
    }

    /**
     * metoda care copiaza in liste noi de consumatori si distribuitori strict informatiile
     * (din consumatori si distribuitori) pe care trebuie sa le afisez
     * @param consumers lista cu toti consumerii
     * @param distributors lista cu toti distributorii
     * @param input tot inputul din fisierul de intrare
     * @return un obiect de tip OutputFormat
     */
    public OutputFormat copy(final List<ConsumerInputData> consumers,
                             final List<DistributorInputData> distributors,
                             final List<ProducersInputData> energyProducers, final Input input) {
        OutputFormat output = new OutputFormat();
        for (int i = 0; i < input.getConsumersData().size(); i++) {
            ConsumerOutputData consumerOut = new ConsumerOutputData(input.getConsumersData()
                    .get(i).getId(), input.getConsumersData().get(i).isBankrupt(),
                    input.getConsumersData().get(i).getBudget());
            output.setConsumers(consumerOut);
        }
        for (int i = 0; i < input.getDistributorsData().size(); i++) {
            DistributorOutputData distributorOut = new DistributorOutputData(input
                    .getDistributorsData().get(i).getId(), input.getDistributorsData().get(i)
                    .getEnergyNeeded(), input.getDistributorsData().get(i).getContractPrice(),
                    input.getDistributorsData().get(i).getInitialBudget(),
                    input.getDistributorsData().get(i).getProducerStrategy(),
                    input.getDistributorsData().get(i).isBankrupt(), input.getDistributorsData()
                    .get(i).getContracts());
            output.setDistributors(distributorOut);
        }
        for (ProducersInputData producer: energyProducers) {
            ProducerOutputData producerOut = new ProducerOutputData(producer.getId(),
                    producer.getMaxDistributors(), producer.getPriceKW(),
                    producer.getEnergyType(), producer.getEnergyPerDistributor(),
                    producer.getMonthlyStats());
            output.setProducers(producerOut);
        }
        return output;
    }
}
