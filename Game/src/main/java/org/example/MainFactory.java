package org.example;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.dsl.views.ScrollingBackgroundView;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.IrremovableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static org.example.EntityType.*;

public class MainFactory implements EntityFactory {

    @Spawns("background")
    public Entity newBackground(SpawnData data) {
        return entityBuilder()
                .at(0, 120)
                .view(new ScrollingBackgroundView(texture("background/forest.png").getImage(), getAppWidth(), getAppHeight()) )
                .zIndex(-1)
                .with(new IrremovableComponent())
                .build();
    }

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return entityBuilder()
                .type(PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),  data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    //Improvisando spawn como nome vazio, para evitar erros no momento de gerar a camada de objetos do mapa
    @Spawns("")
    public Entity newPl(SpawnData data) {
        return entityBuilder()
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"),  data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }


    @Spawns("coin")
    public Entity newCoin(SpawnData data) {
        return entityBuilder()
                .type(COIN)
                .viewWithBBox(new Circle(data.<Integer>get("width") / 2 , Color.GOLD))
                .build();
    }

    @Spawns("feather")
    public Entity newFeather(SpawnData data) {
        Entity player = getGameWorld().getSingleton(EntityType.PLAYER);

        double featheProjectileDirectionX = player.getCenter().getX();
        double featherOriginDirectionY = player.getCenter().getY() - 35;
        double featherOriginDirectionX = featheProjectileDirectionX;
        double changeableScaleFeatherYByPlayerDirectionX = 1;

        if (player.getScaleX() < 0) {
            featheProjectileDirectionX = -player.getCenter().getX();
            featherOriginDirectionX -= 80;
            changeableScaleFeatherYByPlayerDirectionX = -1;
        }

        Point2D direction = new Point2D(featheProjectileDirectionX, 0);

        return  entityBuilder()
                .at(featherOriginDirectionX, featherOriginDirectionY)
                .type(FEATHER)
                .viewWithBBox("normal_feather.png")
                .collidable()
                .with(new ProjectileComponent(direction, 1000))
                .with(new OffscreenCleanComponent())
                .scale(1, changeableScaleFeatherYByPlayerDirectionX)
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {

        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        // SWORDER
        physics.addGroundSensor(new HitBox("GROUND_SENSOR", new Point2D(22, 52), BoundingShape.box(6, 8)));

        // this avoids player sticking to walls
        physics.setFixtureDef(new FixtureDef().friction(0.0f));

        return FXGL.entityBuilder(data)
                .type(PLAYER)
                //.bbox(new HitBox(new Point2D(5,15), BoundingShape.circle(6)))

                //Piolin
                .bbox(new HitBox(new Point2D(25,10), BoundingShape.box(15, 50)))

                // SWORDER
                //.bbox(new HitBox(new Point2D(20,0), BoundingShape.box(10, 28)))

                // CHAR BLUE
                //.bbox(new HitBox(new Point2D(22, 27), BoundingShape.box(10, 28)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new IrremovableComponent())
                .with(new PlayerComponent())
                .build();
    }
}
