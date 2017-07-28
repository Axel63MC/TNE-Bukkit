package net.tnemc.core.common.configurations;

import com.github.tnerevival.core.configurations.Configuration;
import net.tnemc.core.TNE;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
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
public class MessageConfigurations extends Configuration {
  @Override
  public FileConfiguration getConfiguration() {
    return TNE.instance().messageConfigurations;
  }

  @Override
  public List<String> node() {
    List<String> nodes = new ArrayList<>();
    nodes.add("Messages");
    return nodes;
  }

  @Override
  public void load(FileConfiguration configurationFile) {
    configurations.put("Messages.General.NoPerm", "<red>I'm sorry, but you do not have permission to do that.");
    configurations.put("Messages.General.NoPlayer", "<red>Unable to locate player \"$player\"!");
    configurations.put("Messages.General.Saved", "<yellow>Successfully saved all TNE Data!");
    configurations.put("Messages.General.Disabled", "<red>Economy features are currently disabled in this world!");

    configurations.put("Messages.Admin.NoHoldings", "<red>$player has no holdings for the world \"$world\"!");
    configurations.put("Messages.Admin.Holdings", "<white>$player currently has <gold>$amount <white>for world \"$world\"!");
    configurations.put("Messages.Admin.NoTransactions", "<white>$player has no transactions to display.");
    configurations.put("Messages.Admin.Configuration", "<white>The value of $node is currently $value.");
    configurations.put("Messages.Admin.SetConfiguration", "<white>The value of $node has been set to $value.");
    configurations.put("Messages.Admin.ID", "<white>The UUID for $player is $id.");
    configurations.put("Messages.Admin.Exists", "<red>A player with that name already exists.");
    configurations.put("Messages.Admin.Created", "<white>Successfully created account for $player.");
    configurations.put("Messages.Admin.Deleted", "<white>Successfully deleted account for $player.");
    configurations.put("Messages.Admin.Purge", "<white>Successfully purged all economy accounts.");
    configurations.put("Messages.Admin.PurgeWorld", "<white>Successfully purged economy accounts in $world.");
    configurations.put("Messages.Admin.Status", "<white>Status for $player has been changed to <green>$status<white>.");
    configurations.put("Messages.Admin.Reset", "<white>Performed an economy reset using these parameters -  world = $world, currency = $currency, and player = $player.");

    configurations.put("Messages.Account.Locked", "<red>You can't do that with a locked account($player)!");
    configurations.put("Messages.Account.NoTransactions", "<white>You have no transactions to display at this time.");
    configurations.put("Messages.Account.StatusChange", "<white>Your account's status has been changed to <green>$status<white>.");

    configurations.put("Messages.Configuration.NoSuch", "The configuration node $node does not exist.");
    configurations.put("Messages.Configuration.Invalid", "The value you specified for $node is invalid.");
    configurations.put("Messages.Configuration.InvalidFile", "The configuration file you specified is invalid.");
    configurations.put("Messages.Configuration.Get", "The value for node $node is $value.");
    configurations.put("Messages.Configuration.Set", "Successfully set the value for $node to $value.");
    configurations.put("Messages.Configuration.TNEGet", "The parsed value for node $node is $value.");
    configurations.put("Messages.Configuration.Saved", "Successfully saved all modified nodes for file $configuration.");
    configurations.put("Messages.Configuration.SavedAll", "Successfully saved all modified configurations.");
    configurations.put("Messages.Configuration.Undone", "Successfully undid all configuration modifications to $modified.");
    configurations.put("Messages.Configuration.UndoneAll", "Successfully undid all configuration modifications.");

    configurations.put("Messages.Currency.List", "<white>The current currencies for $world are: $currencies.");
    configurations.put("Messages.Currency.Tiers", "<white>The currency \"$currency\" currently has these tiers;<newline>Major: $major_tiers<newline>Minor: $minor_tiers");
    configurations.put("Messages.Currency.AlreadyExists", "<white>A currency with the name $currency already exists in world $world.");
    configurations.put("Messages.Currency.Renamed", "<white>Successfully renamed $currency in world $world to $new_Name.");

    configurations.put("Messages.Module.Info", "<white>==== Module Info for $module ====<newline>Author: $author<newline>Version: $version");
    configurations.put("Messages.Module.Invalid", "<red>Unable to find a module with the name of \"$module\".");
    configurations.put("Messages.Module.List", "<white>This server is currently uses these TNE Modules: $modules.");
    configurations.put("Messages.Module.Loaded", "<white>Loaded module \"$module\" $version created by \"$author\".");
    configurations.put("Messages.Module.Reloaded", "<white>Reloaded module \"$module\".");
    configurations.put("Messages.Module.Unloaded", "<white>Unloaded module \"$module\".");

    configurations.put("Messages.Money.Failed", "<red>Unable to process your transaction at this time.");
    configurations.put("Messages.Money.Given", "<white>You were given <gold>$amount<white>.");
    configurations.put("Messages.Money.Received", "<white>You were paid <gold>$amount <white> by <white> $from.");
    configurations.put("Messages.Money.Taken", "<white>$from took <gold>$amount<white> from you.");
    configurations.put("Messages.Money.Insufficient", "<red>I'm sorry, but you do not have <gold>$amount<red>.");
    configurations.put("Messages.Money.Holdings", "<white>You currently have <gold>$amount<white> on you.");
    configurations.put("Messages.Money.Gave", "<white>Successfully gave $player <gold>$amount<white>.");
    configurations.put("Messages.Money.RecipientSet", "<white>Your balance has been set to <gold>$amount<white>.");
    configurations.put("Messages.Money.Set", "<white>Successfully set $player\'s balance to <gold>$amount<white>.");
    configurations.put("Messages.Money.Paid", "<white>Successfully paid $player <gold>$amount<white>.");
    configurations.put("Messages.Money.Took", "<white>Successfully took <gold>$amount<white> from $player.");
    configurations.put("Messages.Money.Negative", "<red>Amount cannot be a negative value!");
    configurations.put("Messages.Money.SelfPay", "<red>You can't pay yourself!");
    configurations.put("Messages.Money.NoCurrency", "<red>The currency \"$currency\" could not be found in \"$world\".");
    configurations.put("Messages.Money.Converted", "<white>Successfully exchanged \"<gold>$from_amount<white>\" to \"<gold>$amount<white>\".");
    configurations.put("Messages.Money.Top", "<white>=========[<gold>Economy Top<white>]========= Page: $page/$page_top");
    configurations.put("Messages.Money.InvalidFormat", "<red>I'm sorry, but the monetary value you've entered is wrong.");
    configurations.put("Messages.Money.ExceedsCurrencyMaximum", "<red>I'm sorry, but the monetary value you've entered exceeds the maximum possible balance.");
    configurations.put("Messages.Money.ExceedsPlayerMaximum", "<red>I'm sorry, but performing this transaction will place your balance over the maximum allowed.");
    configurations.put("Messages.Money.ExceedsOtherPlayerMaximum", "<red>I'm sorry, but performing this transaction will place $player's balance over the maximum allowed.");

    configurations.put("Messages.Commands.Admin.Backup", "/tne backup - Creates a backup of all server data.");
    configurations.put("Messages.Commands.Admin.Balance", "/tne balance <player> [world] [currency] - Retrieves the balance of a player.<newline>- Player ~ The account owner.<newline>- World ~ The world to retrieve the balance from.<newline>- Currency ~ The currency to retrieve the balance of.");
    configurations.put("Messages.Commands.Admin.Caveats", "/tne caveats - Displays all known caveats for this version of TNE.");
    configurations.put("Messages.Commands.Admin.Create", "/tne create <player> [balance] - Creates a new economy account.<newline>- Player ~ The account owner.<newline>- Balance ~ The starting balance of the account.");
    configurations.put("Messages.Commands.Admin.Delete", "/tne delete <player> - Deletes a player account.<newline>- Player ~ The account owner.");
    configurations.put("Messages.Commands.Admin.ID", "/tne id <player> - Retrieves a player's TNE UUID.<newline>- Player ~ The player you wish to discover the UUID of.");
    configurations.put("Messages.Commands.Admin.Purge", "/tne purge - Deletes all player accounts that have the default balance");
    configurations.put("Messages.Commands.Admin.Recreate", "/tne recreate - Attempts to recreate database tables. WARNING: This will delete all data in the database.");
    configurations.put("Messages.Commands.Admin.Reload", "/tne reload <configuration> - Saves modifications made via command, and reloads a configuration file.<newline>- Configuration ~ The identifier of the configuration to reload. Default is all.");
    configurations.put("Messages.Commands.Admin.Report", "/tne report <report> - File a bug report.<newline>- Report ~ The link to the pastebin with the bug report, use the following format: https://github.com/TheNewEconomy/TNE-Bukkit/blob/master/Report%20Format.md.");
    configurations.put("Messages.Commands.Admin.Save", "/tne save - Force saves all TNE data.");
    configurations.put("Messages.Commands.Admin.Status", "/tne status <player> [status] - Displays, or sets, the current account status of an account.<newline>- Player ~ The account owner.");
    configurations.put("Messages.Commands.Admin.Version", "/tne version - Displays the version of TNE currently running.");

    configurations.put("Messages.Commands.Config.Get", "/tnec get <node> [configuration] - Returns the value of a configuration.<newline>- Node ~ The configuration node to use.<newline>- Configuration ~ The configuration identifier to retrieve the value from. This may be retrieved automatically.");
    configurations.put("Messages.Commands.Config.Save", "/tnec save [configuration] - Saves modifications made to a configuration value via command.<newline>- Configuration ~ The configuration identifier to retrieve the value from.");
    configurations.put("Messages.Commands.Config.Set", "/tnec set <node> <value> [configuration] - Sets a configuration value. This will not save until you do /tnec save.<newline>");
    configurations.put("Messages.Commands.Config.TNEGet", "/tnec tneget <node> [world] [player] - Returns the value of a configuration after TNE takes into account player & world configurations.<newline>- Node ~ The configuration node to use.<newline>- World ~ The name of the world to use for parsing.<newline>- Player ~ The name of the world to use for parsing.");
    configurations.put("Messages.Commands.Config.Undo", "/tnec undo [configuration/all] - Undoes modifications made to configurations.<newline>- Configuration ~ The configuration identifier to retrieve the value from. This may be retrieved automatically.");

    configurations.put("Messages.Commands.Currency.Rename", "/currency rename <currency> <new name> - Renames a currency to a different name.");
    configurations.put("Messages.Commands.Currency.List", "/currency list [world] - Displays the currencies available for a world.<newline> - World ~ The world to use.");
    configurations.put("Messages.Commands.Currency.Tiers", "/currency tiers <currency> [world] - Displays the tiers for a currency.<newline>- Currency ~ The currency to check.<newline>- World ~ The world that the currency belongs to.");

    configurations.put("Messages.Commands.Module.Info", "/tnem info <module> - Displays some information about a module.<newline>- Module ~ The module to look up.");
    configurations.put("Messages.Commands.Module.List", "/tnem list - Lists all loaded TNE modules.");
    configurations.put("Messages.Commands.Module.Load", "/tnem load <module> - Load a module from the modules directory.<newline>- Module ~ The module to load.");
    configurations.put("Messages.Commands.Module.Reload", "/tnem reload <module> - Reloads a module from the modules directory.<newline>- Module ~ The module to reload.");
    configurations.put("Messages.Commands.Module.Unload", "/tnem unload <module> - Unload a module from the server.<newline>- Module ~ The module to unload.");

    configurations.put("Messages.Commands.Money.Balance", "/money balance [world] [currency] - Displays your current holdings.");
    configurations.put("Messages.Commands.Money.Convert", "/money convert <amount> <to currency[:world]> [from currency[:world]] - Convert some of your holdings to another currency.");
    configurations.put("Messages.Commands.Money.Give", "/money give <player> <amount> [world] [currency] - Adds money into your economy, and gives it to a player.");
    configurations.put("Messages.Commands.Money.Pay", "/money pay <player> <amount> [currency] - Use some of your holdings to pay another player.");
    configurations.put("Messages.Commands.Money.Set", "/money set <player> <amount> [world] [currency] - Set the holdings of a player.");
    configurations.put("Messages.Commands.Money.Take", "/money take <player> <amount> [world] [currency] - Removes money from your economy, specifically from a player's balance.");
    configurations.put("Messages.Commands.Money.Top", "/money top [page] [currency:name] [bank:true/false] [world:world] [limit:#] - A list of players with the highest balances.<newline>[page] - The page number to view. Defaults to 1.<newline>[currency] - The name of the currency to get balances from. Defaults to world default. Use overall for all currencies.<newline>[bank] - Whether or not you want to rank players based on highest bank balance. Defaults to false.<newline>[world] - The world name you wish to filter, or all for every world. Defaults to current world. Use overall for all worlds.<newline>[limit] - Limit changes how many players are displayed. Defaults to 10.");

    configurations.put("Messages.Commands.Transaction.Away", "/transaction away [page:#] - Displays transactions that you missed since the last time you were on.");
    configurations.put("Messages.Commands.Transaction.History", "/transaction history [page:#] [world:name/all] - See a detailed break down of your transaction history.<newline>- Page ~ The page number you wish to view.<newline>- World ~ The world name you wish to filter, or all for every world. Defaults to current world.");
    configurations.put("Messages.Commands.Transaction.Info", "/transaction info <uuid> - Displays information about a transaction.<newline>- UUID ~ The id of the transaction.");
    configurations.put("Messages.Commands.Transaction.Void", "/transaction void <uuid> - Undoes a previously completed transaction.<newline>- UUID ~ The id of the transaction.");

    configurations.put("Messages.World.Change", "<white>You have been charged <gold> $amount<white> for changing worlds.");
    configurations.put("Messages.World.ChangeFailed", "<red>I'm sorry, but you need at least <gold>$amount<red> to change worlds.");

    String base = "Messages.Mob.Custom";
    Set<String> keys = configurationFile.getConfigurationSection(base).getKeys(false);
    for(String s : keys) {
      configurations.put(base + "." + s, configurationFile.getString(base + "." + s));
    }

    super.load(configurationFile);
  }
}