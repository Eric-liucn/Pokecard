package com.github.ericliucn.pokecard.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class PCardConfig {

    @Setting
    public List<String> cardLore = new ArrayList<String>()
    {{
        add("&b&l+ &6&l基础信息\n");
        add("&b- &a等级: &d%level%  &b- &a性别: &d%gender%\n");
        add("&b- &a个性: &d%nature%  &b- &a特性: &d%ability%\n");
        add("&b- &a体型: &d%growth%  &b- &a闪光: &d%shiny%\n");
        add("&b&l+ &6&l技能信息\n");
        add("&b- &a技能一: &d%attack_1%  &b- &a技能一: &d%attack_2%\n");
        add("&b- &a技能一: &d%attack_3%  &b- &a技能一: &d%attack_4%\n");
        add("&b&l+ &6&l努力值&e(%ev_percent%)\n");
        add("&b- &a物攻: &d%ev_defend%  &b- &a物攻: &d%ev_attack%\n");
        add("&b- &a特攻: &d%ev_sp_defend%  &b- &a特攻: &d%ev_sp_attack%\n");
        add("&b- &a体力: &d%ev_health%  &b- &a速度: &d%ev_speed%\n");
        add("&b&l+ &6&l个体值&e(%iv_percent%)\n");
        add("&b- &a物攻: &d%iv_defend%  &b- &a物攻: &d%iv_attack%\n");
        add("&b- &a特攻: &d%iv_sp_defend%  &b- &a特攻: &d%iv_sp_attack%\n");
        add("&b- &a体力: &d%iv_health%  &b- &a速度: &d%iv_speed%\n");
    }};
}
