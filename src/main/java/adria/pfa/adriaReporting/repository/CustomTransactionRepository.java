package adria.pfa.adriaReporting.repository;

import adria.pfa.adriaReporting.dao.TransactionDao;
import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.model.Transaction;

import java.util.List;

public interface CustomTransactionRepository {
    List<Transaction> searchTransactionsBy(Client client, TransactionDao transactionDao);
}
