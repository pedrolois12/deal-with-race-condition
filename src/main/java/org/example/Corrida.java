package org.example;


import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Corrida {
    final private static int QUANTIDADE_CORRIDAS = 20;
    final private static String NOMENCLATURA_COMPETIDOR = "Competidor #";
    final private static int QUANTIDADE_COMPETIDORES = 15;


    public Corrida() {

        Map<String, Integer> competitorMap = new HashMap<>();
        List rodadas = new ArrayList();
        int posicao = 0;

        List<Thread> competitors = new ArrayList<>();
        IntStream.range(1, QUANTIDADE_COMPETIDORES + 1).forEach(e -> {
            competitorMap.put(NOMENCLATURA_COMPETIDOR + e, 0);
        });

        Set<Entry<String, Integer>> competitorSet = competitorMap.entrySet();

        for (int controleCorrida = 0; controleCorrida < QUANTIDADE_CORRIDAS; controleCorrida++) {
            Pista pista = new Pista();
            posicao = 0;
            competitorSet.stream()
                    .map(competidorMap -> competidorMap.getKey().toString())
                    .forEach(competidor -> {
                        Competidor comp = new Competidor(competidor, pista);
                        competitors.add(comp.moto);
                    });

            boolean allFinished = true;
            while (allFinished){
               allFinished =  !competitors.stream().noneMatch(Thread::isAlive);
            }
            rodadas.add(pista.ordemChegada.size());
            for (int controle = QUANTIDADE_COMPETIDORES; controle >= 1; controle--) {
                //Tratando quando a lista não esta sincronizada
                if(pista.ordemChegada.size()!=QUANTIDADE_COMPETIDORES && pista.ordemChegada.size() == posicao) break;

                var competitorName = pista.ordemChegada.get(posicao);
                competitorMap.put(competitorName, (int)competitorMap.get(competitorName) + controle);

                posicao++;
            }
        }

        var scoreListOrdered = competitorSet.stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, (o1, o2) -> o2 - o1))
                .map(Entry::getKey)
                .collect(Collectors.toList());

        exibeRelatorioCorrida(scoreListOrdered, competitorMap);
//        exibeRelatorioRodada(rodadas);
    }

    public void exibeRelatorioCorrida(List<String> pontuacoes, Map competitorMap){
        int posicao=1;

        String podio = "==== Podio ====\n";
        String tabela = "==== Tabela de pontos ====\n";
        for (String competitor : pontuacoes) {
            if (posicao <= 3) {
                var texto = competitor + " com " + competitorMap.get(competitor) + System.lineSeparator();
                podio = podio.concat(texto);
            } else {
                var texto = competitor + " com " + competitorMap.get(competitor) + System.lineSeparator();
                tabela = tabela.concat(texto);
            }
            posicao++;
        }
        System.out.println(podio);
        System.out.println(tabela);
    }
    public void exibeRelatorioRodada(List rodada){
         AtomicInteger atomico = new AtomicInteger(1);

         rodada.stream().map(e->{
                return "Na rodada "+(atomico.getAndIncrement())+" -> "+
                        ""+e+" competidores cruzaram a linha de chegada.";
         }).forEach(System.out::println);
    }

    public static void main(String[] args) {
        new Corrida();
    }

    public static class Competidor implements Runnable {
        public String nome;
        Pista pista;
        public Thread moto;

        public Competidor(String nome, Pista pista) {
            this.nome = nome;
            this.moto = new Thread(this, nome);
            this.pista = pista;
            this.moto.start();
        }

        @Override
        public void run() {
            try {
                Thread.sleep(5);
                pista.linhaDeChegada(nome);

            } catch (InterruptedException e) {
                System.err.println("Competidor caiu da moto: " + nome);
            }
        }
    }

    public static class Pista {
        final List<String> ordemChegada = new ArrayList<>();


        synchronized void linhaDeChegada(String competidor) {
            ordemChegada.add(competidor);
        }

        public boolean todoMundoTerminou() {
            System.out.println("Quantidade ordem chegada: "+ordemChegada.size());
            System.out.println("Quantidade competidores: "+QUANTIDADE_COMPETIDORES);
            System.out.println(ordemChegada.size()+1 == QUANTIDADE_COMPETIDORES);
            System.out.println(ordemChegada.size() == QUANTIDADE_COMPETIDORES);
            return ordemChegada.size() == QUANTIDADE_COMPETIDORES;

        }

    }



}
