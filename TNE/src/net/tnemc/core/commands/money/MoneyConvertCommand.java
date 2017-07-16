package net.tnemc.core.commands.money;

import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import com.github.tnerevival.user.IDFinder;
import net.tnemc.core.TNE;
import net.tnemc.core.common.account.WorldFinder;
import net.tnemc.core.common.currency.Currency;
import net.tnemc.core.common.currency.CurrencyFormatter;
import net.tnemc.core.common.transaction.Transaction;
import net.tnemc.core.common.transaction.TransactionCost;
import net.tnemc.core.common.transaction.TransactionResult;
import net.tnemc.core.common.transaction.type.TransactionConversion;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.UUID;

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
 * Created by Daniel on 7/10/2017.
 */
public class MoneyConvertCommand extends TNECommand {

  public MoneyConvertCommand(TNE plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "convert";
  }

  @Override
  public String[] getAliases() {
    return new String[0];
  }

  @Override
  public String getNode() {
    return "tne.money.convert";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public String getHelp() {
    return "Messages.Money.Convert";
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {
    Player player = getPlayer(sender);
    if(arguments.length >= 2) {
      UUID id = IDFinder.getID(sender);
      String worldTo = (arguments[1].contains(":"))? arguments[1].split(":")[1] : WorldFinder.getWorld(sender);
      String currencyTo = (arguments[1].contains(":"))? arguments[1].split(":")[0] : arguments[1];
      Currency to = TNE.instance().manager().currencyManager().get(worldTo, currencyTo);
      Currency from = TNE.instance().manager().currencyManager().get(WorldFinder.getWorld(sender));
      String worldFrom = WorldFinder.getWorld(sender);
      if(arguments.length >= 3) {
        worldFrom = (arguments[2].contains(":"))? arguments[2].split(":")[1] : WorldFinder.getWorld(sender);
        String currencyFrom = (arguments[2].contains(":"))? arguments[2].split(":")[0] : arguments[2];
        from = TNE.instance().manager().currencyManager().get(worldFrom, currencyFrom);
      }

      String parsed = CurrencyFormatter.parseAmount(to, worldTo, arguments[0]);
      if(parsed.contains("Messages")) {
        Message max = new Message(parsed);
        max.addVariable("$currency", to.getSingle());
        max.addVariable("$world", worldTo);
        max.addVariable("$player", player.getDisplayName());
        max.translate(WorldFinder.getWorld(sender), player);
        return false;
      }

      BigDecimal value = new BigDecimal(parsed);
      Transaction transaction = new Transaction(IDFinder.getID(sender), id, worldFrom, new TransactionCost(value, from), new TransactionConversion(worldTo, currencyTo));
      TransactionResult result = transaction.handle();
      Message message = new Message(result.recipientMessage());
      message.addVariable("$player", arguments[0]);
      message.addVariable("$world", worldTo);
      message.addVariable("$currency", to.getSingle());
      message.addVariable("$worldFrom", worldFrom);
      message.addVariable("$currencyFrom", from.getSingle());
      message.addVariable("$amount", CurrencyFormatter.format(currencyTo, worldTo, transaction.getType().recipientBalance()));
      message.translate(WorldFinder.getWorld(sender), IDFinder.getID(sender));
      return result.proceed();
    }
    help(sender);
    return false;
  }
}