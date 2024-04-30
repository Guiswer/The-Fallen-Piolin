package org.example;

import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.AnimationChannelData;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class PlayerComponent extends Component {

    private PhysicsComponent physics;
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk, test;

    private boolean shootInCooldown = false;

    private int jumps = 2;

    public PlayerComponent() {

        Image image = image("walk_piolin1-Sheet.png");
        animIdle = new AnimationChannel(image, 1, 64, 64, Duration.seconds(1), 0, 0);
        animWalk = new AnimationChannel(image, 4, 64, 64, Duration.seconds(1), 1, 3);
        //new AnimationChannel();
        //new AnimationChannelData();
        // SWORDER
        //Image image = image("sworder2.png");
        //Image image = image("char_blue_1.png");
        // animIdle = new AnimationChannel(image, 1, 50, 37, Duration.seconds(1), 0, 0);
        //animWalk = new AnimationChannel(image, 7, 50, 37, Duration.seconds(1), 0, 6);

        // CHAR BLUE
        //animIdle = new AnimationChannel(image, 6, 56, 56, Duration.seconds(1), 0, 5);
        //animWalk = new AnimationChannel(image, 7, 56, 56, Duration.seconds(1), 15, 23);

        texture = new AnimatedTexture(animIdle);
        texture.loop();
    }


    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(25, 21));
        entity.getViewComponent().addChild(texture);

        entity.setScaleX(0.5);
        entity.setScaleY(0.5);
        //PhysicsComponent phy = entity.getComponent(PhysicsComponent.class);

        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (isOnGround) {
                jumps = 2;
            }
        });
    }

    @Override
    public void onUpdate(double tpf) {
        if (physics.isMovingX()) {
            if (texture.getAnimationChannel() != animWalk) {
                texture.loopAnimationChannel(animWalk);
            }
        } else {
            if (texture.getAnimationChannel() != animIdle) {
                texture.loopAnimationChannel(animIdle);
            }
        }
    }

    public void left() {
        getEntity().setScaleX(-0.5);
        physics.setVelocityX(-170);
    }

    public void right() {
        getEntity().setScaleX(0.5);
        physics.setVelocityX(170);
    }

    public void stop() {
        physics.setVelocityX(0);
    }

    public void jump() {
        if (jumps == 0)
            return;

        physics.setVelocityY(-400);

        jumps--;
    }

    public void shoot() {
        if(!shootInCooldown) {
            spawn("feather");
            shootInCooldown = true;

            Timer timer = new Timer();
            long delay = 400;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    shootInCooldown = false;
                }
            };
            timer.schedule(task, delay);
        }
    }
}
