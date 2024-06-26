package org.example;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.core.util.EmptyRunnable;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
import javafx.beans.binding.Bindings;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MyPauseMenu extends FXGLMenu {

    private static final int SIZE = 150;

    private Animation<?> animation;

    public MyPauseMenu() {
        super(MenuType.GAME_MENU);

        getContentRoot().setTranslateX(FXGL.getAppWidth() / 2.0 - SIZE);
        getContentRoot().setTranslateY(FXGL.getAppHeight() / 2.0 - SIZE);

        var shape = Shape.subtract(new Circle(SIZE, SIZE, SIZE), new Rectangle(0, SIZE, SIZE*2, SIZE));
        var shape2 = Shape.subtract(shape, new Rectangle(0, 0, SIZE, SIZE));


        // Shape 1

        shape = Shape.subtract(shape, new Rectangle(SIZE, 0, SIZE, SIZE));

        shape.setStrokeWidth(2.5);
        shape.strokeProperty().bind(
                Bindings.when(shape.hoverProperty()).then(Color.YELLOW).otherwise(Color.BLACK)
        );

        shape.fillProperty().bind(
                Bindings.when(shape.pressedProperty()).then(Color.YELLOW).otherwise(Color.color(0.1, 0.05, 0.0, 0.75))
        );

        shape.setOnMouseClicked(e -> fireResume());


        // Shape 2

        shape2.setStrokeWidth(2.5);
        shape2.strokeProperty().bind(
                Bindings.when(shape2.hoverProperty()).then(Color.YELLOW).otherwise(Color.BLACK)
        );

        shape2.fillProperty().bind(
                Bindings.when(shape2.pressedProperty()).then(Color.YELLOW).otherwise(Color.color(0.1, 0.05, 0.0, 0.75))
        );
        shape2.setOnMouseClicked(e -> FXGL.getGameController().exit());


        // Shape 3

        var shape3 = new Rectangle(SIZE*2, SIZE / 2);

        shape3.setStrokeWidth(2.5);
        shape3.strokeProperty().bind(
                Bindings.when(shape3.hoverProperty()).then(Color.YELLOW).otherwise(Color.BLACK)
        );

        shape3.fillProperty().bind(
                Bindings.when(shape3.pressedProperty()).then(Color.YELLOW).otherwise(Color.color(0.1, 0.05, 0.0, 0.75))
        );

        shape3.setTranslateY(SIZE);

        Text textResume = FXGL.getUIFactoryService().newText("RESUME", Color.WHITE, FontType.GAME, 24.0);
        textResume.setTranslateX(50);
        textResume.setTranslateY(100);
        textResume.setMouseTransparent(true);

        Text textExit = FXGL.getUIFactoryService().newText("EXIT", Color.WHITE, FontType.GAME, 24.0);
        textExit.setTranslateX(200);
        textExit.setTranslateY(100);
        textExit.setMouseTransparent(true);

        Text textOptions = FXGL.getUIFactoryService().newText("OPTIONS", Color.WHITE, FontType.GAME, 24.0);
        textOptions.setTranslateX(110);
        textOptions.setTranslateY(195);
        textOptions.setMouseTransparent(true);

        getContentRoot().getChildren().addAll(shape, shape2, shape3, textResume, textExit, textOptions);

        getContentRoot().setScaleX(0);
        getContentRoot().setScaleY(0);

        animation = FXGL.animationBuilder()
                .duration(Duration.seconds(0.66))
                .interpolator(Interpolators.EXPONENTIAL.EASE_OUT())
                .scale(getContentRoot())
                .from(new Point2D(0, 0))
                .to(new Point2D(1, 1))
                .build();
    }

    @Override
    public void onCreate() {
        animation.setOnFinished(EmptyRunnable.INSTANCE);
        animation.stop();
        animation.start();
    }

    @Override
    protected void onUpdate(double tpf) {
        animation.onUpdate(tpf);
    }
}