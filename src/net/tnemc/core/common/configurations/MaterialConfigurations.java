package net.tnemc.core.common.configurations;

import com.github.tnerevival.core.configurations.Configuration;
import net.tnemc.core.TNE;
import net.tnemc.core.common.objects.MaterialObject;
import org.bukkit.configuration.file.FileConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * The New Economy Minecraft Server Plugin
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * Created by creatorfromhell on 06/30/2017.
 */
public class MaterialConfigurations extends Configuration {
  @Override
  public FileConfiguration getConfiguration() {
    return TNE.instance().materialConfigurations;
  }

  @Override
  public List<String> node() {
    List<String> nodes = new ArrayList<>();
    nodes.add("Materials");
    return nodes;
  }



  private HashMap<String, MaterialObject> materials = new HashMap<>();

  @Override
  public void load(FileConfiguration configurationFile) {
    Set<String> identifiers = TNE.instance().worldConfigurations.getConfigurationSection("Worlds").getKeys(false);

    //Load Materials
    configurations.put("Materials.Enabled", false);
    loadMaterials(configurationFile, "", null, true);
    loadMaterials(configurationFile, "", null, false);
    for(String identifier : identifiers) {
      loadMaterials(TNE.instance().worldConfigurations, "Worlds." + identifier + ".", identifier, true);
      loadMaterials(TNE.instance().worldConfigurations, "Worlds." + identifier + ".", identifier, false);
    }

    identifiers = TNE.instance().playerConfigurations.getConfigurationSection("Players").getKeys(false);
    for(String identifier : identifiers) {
      loadMaterials(TNE.instance().playerConfigurations, "Players." + identifier + ".", identifier, true);
      loadMaterials(TNE.instance().playerConfigurations, "Players." + identifier + ".", identifier, false);
    }
    super.load(configurationFile);
  }

  private void loadMaterials(FileConfiguration configuration, String baseNode, String identifier, boolean item) {
    String base = baseNode + ((item)? "Materials.Items" : "Materials.Blocks");
    if(configuration.contains(base)) {
      Boolean zero = !configuration.contains(base + ".ZeroMessage") || configuration.getBoolean(base + ".ZeroMessage");
      configurations.put(base + ".ZeroMessage", zero);

      Set<String> materialNames = configuration.getConfigurationSection(base).getKeys(false);

      for(String materialName : materialNames) {
        String id = (identifier != null)? identifier + ":" + materialName : materialName;
        String node = base + "." + materialName;

        MaterialObject material = new MaterialObject(materialName);

        BigDecimal buyCost = (configuration.contains(node + ".Buy"))? new BigDecimal(configuration.getDouble(node + ".Buy")) : BigDecimal.ZERO;
        BigDecimal sellCost = (configuration.contains(node + ".Sell"))? new BigDecimal(configuration.getDouble(node + ".Sell")) : BigDecimal.ZERO;
        BigDecimal useCost = (configuration.contains(node + ".Use"))? new BigDecimal(configuration.getDouble(node + ".Use")) : BigDecimal.ZERO;
        BigDecimal craftingCost = (configuration.contains(node + ".Crafting"))? new BigDecimal(configuration.getDouble(node + ".Crafting")) : BigDecimal.ZERO;
        BigDecimal enchantCost = (configuration.contains(node + ".Enchant"))? new BigDecimal(configuration.getDouble(node + ".Enchant")) : BigDecimal.ZERO;
        BigDecimal placeCost = (configuration.contains(node + ".Place"))? new BigDecimal(configuration.getDouble(node + ".Place")) : BigDecimal.ZERO;
        BigDecimal mineCost = (configuration.contains(node + ".Mine"))? new BigDecimal(configuration.getDouble(node + ".Mine")) : BigDecimal.ZERO;
        BigDecimal smeltCost = (configuration.contains(node + ".Smelt"))? new BigDecimal(configuration.getDouble(node + ".Smelt")) : BigDecimal.ZERO;

        material.setItem(item);
        material.setCost(buyCost);
        material.setValue(sellCost);
        material.setUse(useCost);
        material.setCrafting(craftingCost);
        material.setEnchant(enchantCost);
        material.setPlace(placeCost);
        material.setMine(mineCost);
        material.setSmelt(smeltCost);

        materials.put(id, material);
      }
    }
  }

  public Boolean containsMaterial(String name, String world, String player) {
    return materials.containsKey(player + ":" + name) ||
        materials.containsKey(world + ":" + name) ||
        materials.containsKey(name);
  }

  public MaterialObject getMaterial(String name, String world, String player) {
    if(materials.containsKey(player + ":" + name)) return materials.get(player + ":" + name);
    if(materials.containsKey(world + ":" + name)) return materials.get(world + ":" + name);
    return materials.get(name);
  }
}