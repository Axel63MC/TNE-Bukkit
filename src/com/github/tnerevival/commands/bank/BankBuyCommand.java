package com.github.tnerevival.commands.bank;

import com.github.tnerevival.TNE;
import com.github.tnerevival.account.Bank;
import com.github.tnerevival.account.IDFinder;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.Message;
import com.github.tnerevival.core.currency.CurrencyFormatter;
import com.github.tnerevival.core.transaction.TransactionType;
import com.github.tnerevival.utils.AccountUtils;
import com.github.tnerevival.utils.MISCUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BankBuyCommand extends TNECommand {

  public BankBuyCommand(TNE plugin) {
    super(plugin);
  }

  @Override
  public String getName() {
    return "buy";
  }

  @Override
  public String[] getAliases() {
    return new String[0];
  }

  @Override
  public String getNode() {
    return "tne.bank.buy";
  }

  @Override
  public boolean console() {
    return false;
  }

  @Override
  public boolean execute(CommandSender sender, String command, String[] arguments) {
    Player player = getPlayer(sender);
    if(AccountUtils.getAccount(IDFinder.getID(player)).hasBank(IDFinder.findRealWorld(getPlayer(sender)))) {
      new Message("Messages.Bank.Already").translate(IDFinder.findRealWorld(player), player);
      return false;
    }

    MISCUtils.debug("Has bypass? " + player.hasPermission("tne.bank.bypass"));
    if(!player.hasPermission("tne.bank.bypass")) {
      if(AccountUtils.transaction(IDFinder.getID(player).toString(), null, Bank.cost(IDFinder.findRealWorld(player), IDFinder.getID(player).toString()), TransactionType.MONEY_INQUIRY, IDFinder.findRealWorld(player))) {
        AccountUtils.transaction(IDFinder.getID(player).toString(), null, Bank.cost(IDFinder.findRealWorld(player), IDFinder.getID(player).toString()), TransactionType.MONEY_REMOVE, IDFinder.findRealWorld(player));
      } else {
        Message insufficient = new Message("Messages.Money.Insufficient");
        insufficient.addVariable("$amount",  CurrencyFormatter.format(IDFinder.findRealWorld(player), Bank.cost(player.getWorld().getName(), IDFinder.getID(player).toString())));
        insufficient.translate(IDFinder.findRealWorld(player), player);
        return false;
      }
    }
    MISCUtils.debug(IDFinder.getID(player).toString());
    Bank bank = new Bank(IDFinder.getID(player), IDFinder.findRealWorld(player));
    AccountUtils.getAccount(IDFinder.getID(player)).getBanks().put(IDFinder.findRealWorld(player), bank);
    new Message("Messages.Bank.Bought").translate(IDFinder.findRealWorld(player), player);
    return true;
  }

  @Override
  public String getHelp() {
    return "/bank buy - Used to purchase a bank.";
  }

}