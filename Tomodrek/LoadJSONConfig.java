package Tomodrek;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import arc.Core;
import arc.files.Fi;
import arc.util.Log;
import arc.util.serialization.Json;
import arc.util.serialization.JsonReader;
import arc.util.serialization.JsonValue;
import mindustry.gen.Player;

public class LoadJSONConfig {
    public static HashSet<String> shadowBanList = new HashSet();
    public static HashMap<String, String> getStringList = new HashMap();
    public static HashMap<String, Integer> getIntList = new HashMap();
    public static String[] shadowBanList001;
   static Fi fileShadowBan = Core.files.local("config/config/PlayerInShadowBan.json");
  static   Fi fileAdminChecker = Core.files.local("config/config/admins.json");
    static  Fi fileConfigObject = Core.files.local("config/config/ConfigObject.json");
    private static String[] rootAdmins = {"", ""};
    private static String[] admins = {"", ""};
    private static String[] moders = {"75ZDpZN1EzIAAAAA1jY3ZQ==", ""};
    private static String[] reserve = {"uuid-reserve-1", "uuid-reserve-2"};
  //  private static String[] ip = new String[]{"127.0.0.1", "94"};
  public static HashSet<String> ip = new HashSet();

    LoadJSONConfig() {
    }

    public static void loadConfig() {
        try {
            if (!fileShadowBan.exists()) {
                Log.warn("Файл конфигурации не найден по пути: " + fileShadowBan.path(), new Object[0]);
                return;
            }
            String contentA  = fileAdminChecker.readString();
            JsonValue jsonA = new JsonReader().parse(contentA);
            rootAdmins = jsonA.get("rootAdmins").asStringArray();
            admins = jsonA.get("admins").asStringArray();
            moders = jsonA.get("moders").asStringArray();
            reserve = jsonA.get("reserve").asStringArray();
            Collections.addAll(ip, jsonA.get("ip").asStringArray());

            String contentS = fileShadowBan.readString();
            JsonValue jsonS  = (new JsonReader()).parse(contentS);
            shadowBanList001 = jsonS.get("shadowBan").asStringArray();
            if (shadowBanList001 == null) {
                return;
            }
shadowBanList.clear();
            for (String uuid : shadowBanList001) {
                if (uuid != null) {
                    shadowBanList.add(uuid);
                }
            }
            String content003  = fileConfigObject.readString();
            JsonValue json003 = new JsonReader().parse(content003);

for(JsonValue child = json003.child(); child != null; child = child.next()) {
    if(child.isString()) getStringList.put(child.name, child.asString());
    if(child.isNumber()) getIntList.put(child.name, child.asInt());
}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

private static boolean contains(String[] array, String value, Player player) {

    for (String item : array) {
        if (item.equals(value)) {
           if(ip.contains(player.ip())) return true;
        }
    }
    return false;
}

    public static boolean PlayerShadowBanned(String uuid) {
        return shadowBanList.contains(uuid);
    }
    public static void AddPlayerInShadowBan(String uuid) {
        if (uuid != null) {
            shadowBanList.add(uuid);
        }
    }
        public static void SaveShadowBansList() {
            if (fileShadowBan.exists()) {
                HashMap<String, Object> wrapper = new HashMap();
                wrapper.put("shadowBan", shadowBanList.toArray(new String[0]));
                Json json = new Json();
                fileShadowBan.writeString(json.prettyPrint(wrapper));
            }

        }

    public static int getLevel(String uuid, Player player) {
        if (contains(rootAdmins, uuid, player)) return 0;
        if (contains(admins, uuid, player)) return 1;
        if (contains(moders, uuid, player)) return 2;
        if (contains(reserve, uuid, player)) return 3;
        return 4;
    }

    public static boolean isRoot(String uuid, Player player) { return getLevel(uuid, player) == 0; }
    public static boolean isAdmin(String uuid, Player player) { return getLevel(uuid, player) <= 1; }
    public static boolean isModer(String uuid, Player player) { return getLevel(uuid, player) <= 2; }
    public static boolean isReserve(String uuid, Player player) { return getLevel(uuid, player) <= 3; }
    static public String getConfig(String ObjectS) {
String output002 = getStringList.get(ObjectS);
if(output002 == null) {
    Log.err("В Tomodrek.LoadJSONConfig, в getConfig ошибка:   " +ObjectS + " Вызвал null, output001 будет: 'null'");
    return "null";
}
return output002;

    }
    public static int getConfigInt(String ObjectS) {
      //  int output002 = GetIntList.get(ObjectS);

return getIntList.getOrDefault(ObjectS, 60);
    }
}