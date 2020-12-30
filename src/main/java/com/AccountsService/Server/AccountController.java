package com.AccountsService.Server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.javamoney.moneta.Money;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

	String senderAccountNotFoundexceptionMassage = "There isn´t any transfer for sender Id : ";
	String receiverAccountNotFoundexceptionMassage = "There isn´t any transfer for receiver Id : ";

	private final AccountRepository repository;
	private final TransferRepository transferRepository;

	AccountController(AccountRepository repository, TransferRepository transferRepository) {
		this.repository = repository;
		this.transferRepository = transferRepository;
	}

	private final AtomicLong counter = new AtomicLong();
	Money sumAmtCHF = Money.of(1, "CHF");
	Currency cur = Currency.getInstance("CHF");

	@GetMapping("/account")
	public
	List<Account> all() {
		return repository.findAll();
	}

	@PostMapping("/account")
	public
	Account newAccount(@RequestBody AccountAux newAccountAux) {// account aux pq p json nao controi o MONEY

		// validate, si for false nao pode fazer post de saldo negativo
//		if(newAccount.isTreasury()==false) {
//			
//			
//		}
		Money money = Money.of(newAccountAux.getMoney(), newAccountAux.getCurrency().getCurrencyCode());
		Account newAccount = new Account();
		newAccount.setId(newAccountAux.getId());
		newAccount.setCurrency(newAccountAux.getCurrency());
		newAccount.setName(newAccountAux.getName());
		newAccount.setTreasury(newAccountAux.isTreasury());

		newAccount.setMoney(money);

		return repository.save(newAccount);
	}

	@GetMapping("/account/{id}")
	Account one(@PathVariable int id) {
		return repository.findById(id).orElseThrow(() -> new NotFoundException(id, "Account not found"));
	}

	@PutMapping("/account/{id}")
	Account replaceAccount(@RequestBody AccountAux newAccountAux, @PathVariable int id) {// usar o aux provavel
		Money money = Money.of(newAccountAux.getMoney(), newAccountAux.getCurrency().getCurrencyCode());
		Account newAccount = new Account();
		newAccount.setCurrency(newAccountAux.getCurrency());
		newAccount.setName(newAccountAux.getName());
		newAccount.setMoney(money);// verificar se muda o money com a mudança de currency
		// validate, si for false nao pode fazer put de saldo negativo
		if (newAccount.isTreasury() == false) {

		}

		return repository.findById(id)// possivel erro, accho nq nao acha e pula
				.map(account -> {
					account.setName(newAccount.getName());
					account.setCurrency(newAccount.getCurrency());
					account.setMoney(newAccount.getMoney());// verificar se muda o money com a mudança de currency
					// account.setTreasury(newAccount.isTreasury());
					return repository.save(account);
				}).orElseThrow(() -> new NotFoundException(id, "Account not found"));

	}

	@PostMapping("/account/{id}/transfer")
	Transfer accountTransfer(@RequestBody Transfer newTransfer, @PathVariable int id) {
//subtraçao em diferentes currency
		// se mudar o currency muda o saldo???
		
		
		
		return repository.findById(id).map(account -> {
			newTransfer.getMoney().abs();
			Money moneyTransfer = Money.of(newTransfer.getMoney(), account.getCurrency().getCurrencyCode());// currency

					
			
			if (!(account.getMoney().subtract(moneyTransfer)).isNegative()) {

				

				return repository.findById(newTransfer.getReceivingAccountid())
					.map(receivingAccount -> {
						if(receivingAccount.getMoney().getCurrency().equals(moneyTransfer.getCurrency())) {
							account.setMoney(account.getMoney().subtract(moneyTransfer));
							receivingAccount.setMoney(receivingAccount.getMoney().add(moneyTransfer));
							repository.save(account);
							repository.save(receivingAccount);
							newTransfer.setStatus("done");
							newTransfer.setSendingAccountid(id);
							transferRepository.save(newTransfer);
						}else {
							newTransfer.setStatus("Failed Transfer!Accounts have different Currency "+moneyTransfer.getCurrency()+"/"+receivingAccount.getMoney().getCurrency());
							newTransfer.setSendingAccountid(id);
							transferRepository.save(newTransfer);
						}

					return newTransfer;

					
					
				}).orElseThrow(() -> new NotFoundException(newTransfer.getReceivingAccountid(),
						"Receiving Account not found"));

			} else {
				if (account.isTreasury()) {
					

					repository.findById(newTransfer.getReceivingAccountid())
					.map(receivingAccount -> {
						
						if(receivingAccount.getMoney().getCurrency().equals(moneyTransfer.getCurrency())) {
							account.setMoney(account.getMoney().subtract(moneyTransfer));
							receivingAccount.setMoney(receivingAccount.getMoney().add(moneyTransfer));
							repository.save(account);
							repository.save(receivingAccount);
							newTransfer.setStatus("done");
							newTransfer.setSendingAccountid(id);
							transferRepository.save(newTransfer);
						}else {
							newTransfer.setStatus("Failed Transfer!Accounts have different Currency "+moneyTransfer.getCurrency()+"/"+receivingAccount.getMoney().getCurrency());
							newTransfer.setSendingAccountid(id);
							transferRepository.save(newTransfer);
						}

						

						return newTransfer;
					}).orElseThrow(() -> new NotFoundException(newTransfer.getReceivingAccountid(),
							"Receiving Account not found"));

				} else {
					newTransfer.setStatus("No Balance for Transfer, your balance is : " + account.getMoney());
					newTransfer.setSendingAccountid(id);
					transferRepository.save(newTransfer);
					
				}

			}

			return newTransfer;

		})
				.orElseThrow(() -> new NotFoundException(id, "Account not found"));

	}

	@DeleteMapping("/account/{id}")
	void deleteAccount(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@GetMapping("/transfers")
	List<Transfer> allTransfer() {
		return transferRepository.findAll();
	}

	@GetMapping("/transfer/{id}")
	Transfer transferByTransferId(@PathVariable int id) {
		return transferRepository.findById(id).orElseThrow(() -> new NotFoundException(id, "Transfer not found"));
	}

	@GetMapping("/transfers/{id}/asSender")
	List<Transfer> transferAsSenderId(@PathVariable int id) {
		if (repository.findById(id).isEmpty()) {
			senderAccountNotFoundexceptionMassage = "There is no account with id : ";
		}
		return transferRepository.findBySendingAccountid(id)
				.orElseThrow(() -> new NotFoundException(senderAccountNotFoundexceptionMassage + id));

	}

	@GetMapping("/transfers/{id}/asReceiver")
	List<Transfer> transferAsReceiverId(@PathVariable int id) {
		if (repository.findById(id).isEmpty()) {
			receiverAccountNotFoundexceptionMassage = "There is no account with id : ";
		}
		return transferRepository.findByReceivingAccountid(id)
				.orElseThrow(() -> new NotFoundException(receiverAccountNotFoundexceptionMassage + id));
	}
	
	// unit e compond test//git hub
	// readme
// exepxtion de transfeer 

}
