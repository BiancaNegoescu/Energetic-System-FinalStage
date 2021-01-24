package strategies;

import fileinput.DistributorInputData;
import fileinput.ProducersInputData;

import java.util.List;

public interface EnergyStrategy {
     /**
      * antetul metodei care va fi implementata de toate clasele care implementeaza aceasta
      * interfata
      * @param producers lista cu toti producerii
      * @param distributor distribuitorul pentru care se aplica strategia
      * @param round numarul rundei
      */
     void applyStrategy(List<ProducersInputData> producers,
                              DistributorInputData distributor, int round);
}
