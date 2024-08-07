/*
    This file is part of the HeavenMS MapleStory Server, commands OdinMS-based
    Copyleft (L) 2016 - 2019 RonanLana

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/*
   @Author: Arthur L - Refactored command content into modules
*/
package org.gms.client.command.commands.gm3;

import org.gms.client.Character;
import org.gms.client.Client;
import org.gms.client.Disease;
import org.gms.client.command.Command;
import org.gms.server.life.MobSkill;
import org.gms.server.life.MobSkillFactory;
import org.gms.server.life.MobSkillType;
import org.gms.server.maps.MapObject;
import org.gms.server.maps.MapObjectType;
import org.gms.util.I18nUtil;

import java.util.Arrays;
import java.util.Optional;

public class DebuffCommand extends Command {
    {
        setDescription(I18nUtil.getMessage("DebuffCommand.message1"));
    }

    @Override
    public void execute(Client c, String[] params) {
        Character player = c.getPlayer();
        if (params.length < 1) {
            player.yellowMessage(I18nUtil.getMessage("DebuffCommand.message2"));
            return;
        }

        Disease disease = null;
        Optional<MobSkill> skill = Optional.empty();

        switch (params[0].toUpperCase()) {
            case "SLOW" -> {
                disease = Disease.SLOW;
                skill = MobSkillFactory.getMobSkill(MobSkillType.SLOW, 7);
            }
            case "SEDUCE" -> {
                disease = Disease.SEDUCE;
                skill = MobSkillFactory.getMobSkill(MobSkillType.SEDUCE, 7);
            }
            case "ZOMBIFY" -> {
                disease = Disease.ZOMBIFY;
                skill = MobSkillFactory.getMobSkill(MobSkillType.UNDEAD, 1);
            }
            case "CONFUSE" -> {
                disease = Disease.CONFUSE;
                skill = MobSkillFactory.getMobSkill(MobSkillType.REVERSE_INPUT, 2);
            }
            case "STUN" -> {
                disease = Disease.STUN;
                skill = MobSkillFactory.getMobSkill(MobSkillType.STUN, 7);
            }
            case "POISON" -> {
                disease = Disease.POISON;
                skill = MobSkillFactory.getMobSkill(MobSkillType.POISON, 5);
            }
            case "SEAL" -> {
                disease = Disease.SEAL;
                skill = MobSkillFactory.getMobSkill(MobSkillType.SEAL, 1);
            }
            case "DARKNESS" -> {
                disease = Disease.DARKNESS;
                skill = MobSkillFactory.getMobSkill(MobSkillType.DARKNESS, 1);
            }
            case "WEAKEN" -> {
                disease = Disease.WEAKEN;
                skill = MobSkillFactory.getMobSkill(MobSkillType.WEAKNESS, 1);
            }
            case "CURSE" -> {
                disease = Disease.CURSE;
                skill = MobSkillFactory.getMobSkill(MobSkillType.CURSE, 1);
            }
        }

        if (disease == null || skill.isEmpty()) {
            player.yellowMessage(I18nUtil.getMessage("DebuffCommand.message2"));
            return;
        }

        for (MapObject mmo : player.getMap().getMapObjectsInRange(player.getPosition(), 777777.7, Arrays.asList(MapObjectType.PLAYER))) {
            Character chr = (Character) mmo;

            if (chr.getId() != player.getId()) {
                chr.giveDebuff(disease, skill.get());
            }
        }
    }
}
