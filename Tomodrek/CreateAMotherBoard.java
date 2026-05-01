package Tomodrek;
import arc.math.geom.*;
import arc.scene.event.InputEvent;
import arc.scene.event.InputListener;
import arc.util.*;
import arc.scene.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.input.*;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;

import arc.math.geom.*;
import arc.util.*;
import arc.scene.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.input.*;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;
import mindustry.graphics.*; // Для Pal

public class CreateAMotherBoard extends BaseDialog {
    Table board; // Используем Table, у нее точно есть фон

    public CreateAMotherBoard() {
        super("Редактор платы");
        addCloseButton();

        // Создаем таблицу-контейнер
        board = new Table();

        // Устанавливаем фон через ячейку в основном контенте диалога
        // .get() возвращает Cell, у которой в V7/V8 метод называется setBackground или background
        cont.add(board).size(800f, 600f).pad(20f);

        // Самый надежный способ задать фон в Mindustry:
        board.setBackground(Tex.pane);

        addComponent("CPU", 100, 100);
    }

    public void addComponent(String name, float x, float y) {
        // Используем Styles.black6 (он есть всегда)
        Table comp = new Table(Styles.black6);
        comp.add(name).color(Pal.accent).pad(10f);
        comp.setSize(64f, 48f);

        // Устанавливаем позицию вручную
        comp.setPosition(x, y);

        comp.addListener(new InputListener() {
            float lastX, lastY;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, KeyCode button) {
                Vec2 v = comp.localToParentCoordinates(Tmp.v1.set(x, y));
                lastX = v.x;
                lastY = v.y;
                comp.toFront();
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Vec2 v = comp.localToParentCoordinates(Tmp.v1.set(x, y));
                comp.moveBy(v.x - lastX, v.y - lastY);
                lastX = v.x;
                lastY = v.y;
            }
        });

        // Добавляем напрямую в board, игнорируя сеточную верстку таблицы
        board.addChild(comp);
    }
}