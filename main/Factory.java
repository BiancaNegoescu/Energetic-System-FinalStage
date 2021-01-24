package main;

import common.Constants;

public final class Factory {

    private static Factory singleFactoryInstance = null;

    private Factory() {

    }
    /**
     * se creaza un obiect al clasei cand este nevoie de el (Lazy Initialization)
     * @return o instanta a clasei
     */
    public static Factory getInstance() {
        if (singleFactoryInstance == null) {
            singleFactoryInstance = new Factory();
        }
        return singleFactoryInstance;
    }

    /**
     * metoda comuna celor 2 entitati care verifica daca consumatorul/ distribuitorul a dat
     * faliment
     * @return null
     */
    public CommonMethods isBankrupt(final String entityType) {
        if (entityType == null) {
            return null;
        }
        if (entityType.equals(Constants.CONSUMER)) {
            return Consumer.getInstance();
        }
        if (entityType.equals(Constants.DISTRIBUTOR)) {
            return Distributor.getInstance();
        }
        return null;
    }


    /**
     * metoda comuna celor 2 entitati care calculeaza pentru fiecare bugetul lunar
     * @param entityType un string consumer sau distributor
     * @return o instanta a clasei sau null
     */
    public CommonMethods computeMonthlyBudget(final String entityType) {
        if (entityType == null) {
            return null;
        }
        if (entityType.equals(Constants.CONSUMER)) {
            return Consumer.getInstance();
        }
        if (entityType.equals(Constants.DISTRIBUTOR)) {
            return Distributor.getInstance();
        }
        return null;
    }

    /**
     * metoda comuna celor 2 entitati care sterge un contract
     * @param entityType consumer sau distributor
     * @return o instanta a clasei sau null
     */
    public CommonMethods eraseContract(final String entityType) {
        if (entityType == null) {
            return null;
        }
        if (entityType.equals(Constants.CONSUMER)) {
            return Consumer.getInstance();
        }
        if (entityType.equals(Constants.DISTRIBUTOR)) {
            return Distributor.getInstance();
        }
        return null;
    }
}
