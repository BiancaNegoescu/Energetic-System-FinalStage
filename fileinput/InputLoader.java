package fileinput;

import common.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class InputLoader {
    private final String inputPath;

    public InputLoader(final String inputPath) {
        this.inputPath = inputPath;
    }

    public String getInputPath() {
        return inputPath;
    }

    public Input readData() {
        JSONParser jsonParser = new JSONParser();
        long numberofTurns = 0;
        List<ConsumerInputData> consumers = new ArrayList<>();
        List<DistributorInputData> distributors = new ArrayList<>();
        List<MonthlyUpdateInputData> monthlyUpdates = new ArrayList<>();
        List<ProducersInputData> producers = new ArrayList<>();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser
                    .parse(new FileReader(inputPath));
            numberofTurns = (long) jsonObject.get(Constants.NUMBEROFTURNS);
            JSONObject initialData = (JSONObject) jsonObject
                    .get(Constants.INITIALDATA);
            JSONArray jsonDistributors = (JSONArray)
                    initialData.get(Constants.DISTRIBUTORS);
            JSONArray jsonProducers = (JSONArray) initialData.get(Constants.PRODUCERS);
            JSONArray jsonConsumers = (JSONArray)
                    initialData.get(Constants.CONSUMERS);
            JSONArray jsonMonthlyUpdates = (JSONArray)
                    jsonObject.get(Constants.MONTHLYUPDATES);
            if (jsonConsumers != null) {
                for (Object jsonConsumer : jsonConsumers) {
                    consumers.add(new ConsumerInputData(
                            Integer.parseInt(((JSONObject) jsonConsumer)
                                    .get(Constants.ID).toString()),
                            Integer.parseInt(((JSONObject) jsonConsumer)
                                    .get(Constants.INITIALBUDGET)
                                    .toString()),
                            Integer.parseInt(((JSONObject) jsonConsumer)
                                    .get(Constants.MONTHLYINCOME).toString()), "consumer"
                    ));
                }
            } else {
                System.out.println("NU EXISTA CONSUMATORI");
            }
            if (jsonDistributors != null) {
                for (Object jsonDistributor : jsonDistributors) {

                    ArrayList<ContractsInputData> contracts =
                            new ArrayList<>();

                    if (((JSONObject) jsonDistributor)
                            .get(Constants.CONTRACTS) != null) {
                        for (Object iterator : (JSONArray)
                                ((JSONObject) jsonDistributor)
                                        .get(Constants.CONTRACTS)) {
                            contracts.add(new ContractsInputData(
                                    Integer.parseInt(((JSONObject) iterator)
                                            .get(Constants.ID)
                                            .toString()),
                                    Integer.parseInt(((JSONObject) iterator)
                                            .get(Constants.PRICE).toString()),
                                    Integer.parseInt(((JSONObject) iterator)
                                            .get(Constants.REMAINED_MONTHS)
                                            .toString())
                            ));
                        }
                    } else {
                        contracts = null;
                    }
                    distributors.add(new DistributorInputData(
                            Integer.parseInt(((JSONObject) jsonDistributor)
                                    .get(Constants.ID).toString()),
                            Integer.parseInt(((JSONObject) jsonDistributor)
                                    .get(Constants.INITIALBUDGET)
                                    .toString()),
                            Integer.parseInt(((JSONObject) jsonDistributor)
                                    .get(Constants.INITIALINFRASTRUCTURECOST)
                                    .toString()),
                            Integer.parseInt(((JSONObject) jsonDistributor)
                                    .get(Constants.ENERGYNEEDED_KW)
                                    .toString()),
                            Integer.parseInt(((JSONObject) jsonDistributor)
                                    .get(Constants.CONTRACTLENGTH).toString()),
                            (String) ((JSONObject) jsonDistributor).get(Constants.PRODUCERSTRATEGY),
                            contracts, "distributor"
                    ));
                }
            } else {
                System.out.println("NU EXISTA DISTRIBUITORI");
            }

            if (jsonProducers != null) {
                for (Object jsonProducer: jsonProducers) {
                    producers.add(new ProducersInputData(
                            Integer.parseInt(((JSONObject) jsonProducer)
                                    .get(Constants.ID).toString()),
                            (String) ((JSONObject) jsonProducer).get(Constants.ENERGY_TYPE),
                            Integer.parseInt(((JSONObject) jsonProducer)
                                    .get(Constants.MAXDISTRIBUTORS).toString()),
                            Float.parseFloat(((JSONObject) jsonProducer)
                                    .get(Constants.PRICE_KW).toString()),
                            Integer.parseInt(((JSONObject) jsonProducer)
                                    .get(Constants.ENERGYPERDISTRIBUTOR).toString())
                    ));
                }
            } else {
                System.out.println("NU EXISTA PRODUCATORI");
            }

            if (jsonMonthlyUpdates != null) {
                for (Object jsonIterator : jsonMonthlyUpdates) {

                    ArrayList<Consumer> newConsumers = new ArrayList<>();

                    if (((JSONObject) jsonIterator).
                            get(Constants.NEWCONSUMERS) != null) {
                        for (Object iterator : (JSONArray)
                                ((JSONObject) jsonIterator).get(Constants
                                        .NEWCONSUMERS)) {
                            newConsumers.add(new Consumer(
                                    Integer.parseInt(((JSONObject) iterator)
                                            .get(Constants.ID).toString()),
                                    Integer.parseInt(((JSONObject) iterator)
                                            .get(Constants.INITIALBUDGET)
                                            .toString()),
                                    Integer.parseInt(((JSONObject) iterator).
                                            get(Constants.MONTHLYINCOME)
                                            .toString())
                            ));
                        }
                    } else {
                        newConsumers = null;
                    }
                    ArrayList<DistributorChangesInputData> distributorsChanges =
                            new ArrayList<>();

                    if (((JSONObject) jsonIterator).
                            get(Constants.DISTRIBUTORCHANGES) != null) {
                        for (Object iterator : (JSONArray)
                                ((JSONObject) jsonIterator)
                                        .get(Constants.DISTRIBUTORCHANGES)) {
                            distributorsChanges.add(new DistributorChangesInputData(
                                    Integer.parseInt(((JSONObject) iterator)
                                            .get(Constants.ID)
                                            .toString()),
                                    Integer.parseInt(((JSONObject) iterator)
                                            .get(Constants.INFRASTRUCTURECOST)
                                            .toString())
                            ));
                        }
                    } else  {
                        distributorsChanges = null;
                    }

                    ArrayList<ProducerChangesInputData> producersChanges =
                            new ArrayList<>();

                    if (((JSONObject) jsonIterator).
                            get(Constants.PRODUCERCHANGES) != null) {
                        for (Object iterator : (JSONArray)
                                ((JSONObject) jsonIterator)
                                        .get(Constants.PRODUCERCHANGES)) {
                            producersChanges.add(new ProducerChangesInputData(
                                    Integer.parseInt(((JSONObject) iterator)
                                            .get(Constants.ID)
                                            .toString()),
                                    Integer.parseInt(((JSONObject) iterator)
                                            .get(Constants.ENERGYPERDISTRIBUTOR)
                                            .toString())
                            ));
                        }
                    } else  {
                        producersChanges = null;
                    }

                    monthlyUpdates.add(new MonthlyUpdateInputData(newConsumers,
                            distributorsChanges, producersChanges));
                }
            } else {
                System.out.println("NU EXISTA MONTHLY UPDATES");
            }
            if (jsonConsumers == null) {
                consumers = null;
            }
            if (jsonDistributors == null) {
                distributors = null;
            }
            if (jsonProducers == null) {
                producers = null;
            }
            if (jsonMonthlyUpdates == null) {
                monthlyUpdates = null;
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return new Input(numberofTurns, consumers,
                distributors, producers, monthlyUpdates);
    }
}
