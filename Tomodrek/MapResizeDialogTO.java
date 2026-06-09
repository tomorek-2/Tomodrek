package Tomodrek;

import arc.math.*;
import arc.scene.ui.TextField.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.editor.MapResizeDialog;
import mindustry.ui.dialogs.*;

import static mindustry.Vars.*;

public class MapResizeDialogTO extends MapResizeDialog {
    public static int minSize = 1, maxSize = 8192, increment = 50;

    int width, height, shiftX, shiftY;

    public MapResizeDialogTO(MapResizeDialog.ResizeListener cons) {
        // Вызываем конструктор родителя
        super(cons);

        // Зачищаем старый заголовок и ставим наш
        this.title.setText("@editor.resizemap");

        this.shown(() -> {
            this.cont.clear();
            width = mindustry.Vars.editor.width();
            height = mindustry.Vars.editor.height();

            arc.scene.ui.layout.Table table = new arc.scene.ui.layout.Table();

            for(boolean w : arc.math.Mathf.booleans){
                table.add(w ? "@width" : "@height").padRight(8f);
                table.defaults().height(60f).padTop(8); // Исправили высоту на положительную

                table.field((w ? width : height) + "", arc.scene.ui.TextField.TextFieldFilter.digitsOnly, value -> {
                    int val = Integer.parseInt(value);
                    if(w) width = val; else height = val;
                }).valid(value -> arc.util.Strings.canParsePositiveInt(value) && Integer.parseInt(value) <= maxSize && Integer.parseInt(value) >= minSize).maxTextLength(30);

                table.row();
            }

            for(boolean x : arc.math.Mathf.booleans){
                table.add(x ? "@editor.shiftx" : "@editor.shifty").padRight(8f);
                table.defaults().height(60f).padTop(8);

                table.field((x ? shiftX : shiftY) + "", value -> {
                    int val = Integer.parseInt(value);
                    if(x) shiftX = val; else shiftY = val;
                }).valid(arc.util.Strings::canParseInt).maxTextLength(4);

                table.row();
            }

            this.cont.row();
            this.cont.add(table);
        });

        this.buttons.defaults().size(200f, 50f);
        this.buttons.clearChildren(); // Очищаем старые ванильные кнопки Анюка
        this.buttons.button("@cancel", this::hide);
        this.buttons.button("@ok", () -> {
            cons.get(width, height, shiftX, shiftY);
            this.hide();
        });
    }
}