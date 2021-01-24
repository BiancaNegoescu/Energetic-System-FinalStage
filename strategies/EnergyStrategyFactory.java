package strategies;

public final class EnergyStrategyFactory {

    private static EnergyStrategyFactory singleEnergyStrategyFactoryInstance = null;

    private EnergyStrategyFactory() {

    }
    /**
     * se creaza un obiect al clasei atunci cand este nevoie de el (Lazy Initialization)
     * @return o instanta a clasei
     */
    public static EnergyStrategyFactory getInstance() {
        if (singleEnergyStrategyFactoryInstance == null) {
            singleEnergyStrategyFactoryInstance = new EnergyStrategyFactory();
        }
        return singleEnergyStrategyFactoryInstance;
    }

    /**
     * metoda care returneaza o instanta a unei clase care aplica o strategie in functie de tipul de
     * strategia primita ca parametru
     * @param strategyType tipul de strategie
     * @return o instanta a unei clase care aplica o strategie
     */
    public EnergyStrategy createStategy(String strategyType) {

        if (strategyType != null && strategyType
                .equals(EnergyChoiceStrategyType.GREEN.toString())) {
            return new GreenStrategy();
        }
        if (strategyType != null && strategyType
                .equals(EnergyChoiceStrategyType.PRICE.toString())) {
            return new PriceStrategy();
        }
        if (strategyType != null && strategyType
                .equals(EnergyChoiceStrategyType.QUANTITY.toString())) {
            return new QuantityStrategy();
        }
        return null;
    }
}
