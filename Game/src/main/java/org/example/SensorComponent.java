package org.example;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class SensorComponent extends Component {
        @Override
        public void onUpdate(double tpf) {
            Entity player = getGameWorld().getSingleton(EntityType.PLAYER);

            //System.out.println("awjdiawdiuanbwduiabwdiubawub");
            if (getEntity().distance(player) < 100) {
                //System.out.println("PERTINHO");

                getEntity().getComponent(EnemyComponent.class).atirar();

            } else {

            }
        }
}

