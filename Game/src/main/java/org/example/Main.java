package org.example;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.core.util.LazyValue;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.level.Level;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.view.KeyView;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

// Classe principal para rodar o programa!
// extende a classe GameApplication do FXGL 
// responsável por disponibilizar os métodos de configuração 
// do jogo
public class Main extends GameApplication {

// Injetando a classe player de forma global
    private Entity player;

// método de configurações usado para
// definir o tamanho da tela entre outros.
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(15*70); // 1050
        settings.setHeight(10*70); // 700
        System.out.println(settings.getMenuKey());
        settings.setGameMenuEnabled(true);
        settings.setDeveloperMenuEnabled(true);

        settings.setSceneFactory(new SceneFactory() {
            @Override
            public LoadingScene newLoadingScene() {
                return new MainLoadingScene();
            }
        });

        //settings.getCollisionDetectionStrategy();
        //settings.getEnabledMenuItems();
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
    }



// método responsável por criar os atalhos das teclas
// e definir as ações
    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.A, VirtualButton.LEFT);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.D, VirtualButton.RIGHT);

        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).jump();
            }
        }, KeyCode.W, VirtualButton.A);

        getInput().addAction(new UserAction("Shoot") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).shoot();
            }
        }, MouseButton.PRIMARY);
    }


// método acionado antes do init para definir
// o som de fundo e a classe de fábrica (factory)
// que é responsável por saber a criação de cada entidade 
// e as suas características quando invocada no jogo
    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(0.03);
        loopBGM("you_won_the_battle_but_not_the_war.wav");
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new MainFactory());

        player = null;


        // READ MORE...
        //if (player != null) {
        //    player.getComponent(PhysicsComponent.class).overwritePosition(new Point2D(50, 50));
        //    player.setZIndex(Integer.MAX_VALUE);
        //}

        spawn("background");
        setLevelFromMap("tmx/mapa.tmx");

        player = spawn("player", 0, 0);

        Viewport viewport = getGameScene().getViewport();
       // viewport.setBounds(-1500, 0, 250 * 70, getAppHeight());
        viewport.bindToEntity(player, getAppWidth() / 2, getAppHeight() / 2);
        viewport.setZoom(2);
        viewport.setLazy(true);
    }



// método responsável por definir as configurações 
// de física do jogo como a gravidade para as entidades
// e as ações de colisão entre entidades
    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 760);
        onCollisionBegin(EntityType.FEATHER, EntityType.ENEMY, (bullet, enemy) -> {
            bullet.removeFromWorld();
            enemy.removeFromWorld();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}