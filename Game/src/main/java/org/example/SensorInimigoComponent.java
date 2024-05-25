package org.example;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

/*
Componente de sensor para que o inimigo possa detectar elementos do jogo
e reagir de forma aleatória
*/
public class SensorInimigoComponent extends Component {

        private boolean emEspera = false;

        @Override
        public void onUpdate(double tpf) {
            if (!emEspera) {
                emEspera = true;

                // Thread - timer para tirar o tempo de espera
                Timer timer = new Timer();
                long delay = 600;
                TimerTask tarefa = new TimerTask() {
                    @Override
                    public void run() {
                        emEspera = false;
                    }
                };
                timer.schedule(tarefa, delay);

                // Pegando a entidade do inimigo
                Entity enemy = getGameWorld().getSingleton(EntityType.ENEMY);

                // Pegando a entidade mais proxíma sem filtro
                Optional<Entity> entidadeMaisProxima = getGameWorld().getClosestEntity(enemy, (e) -> {
                    return true;
                });

                // Decisão de tiro aleatória
                if (getEntity().distance(entidadeMaisProxima.get()) < 100) {
                    Random random = new Random();
                    boolean decisaoAleatoriaDeTiro = random.nextBoolean();

                    if (decisaoAleatoriaDeTiro) {
                        getEntity().getComponent(EnemyComponent.class).atirar(entidadeMaisProxima.get());
                    }
                }
            }
        }
}