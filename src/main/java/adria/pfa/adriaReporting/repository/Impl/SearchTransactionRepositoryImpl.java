package adria.pfa.adriaReporting.repository.Impl;

import adria.pfa.adriaReporting.dao.TransactionDao;
import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.model.Transaction;
import adria.pfa.adriaReporting.repository.SearchTransactionRepository;
import adria.pfa.adriaReporting.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SearchTransactionRepositoryImpl implements SearchTransactionRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> searchTransactionsByClientAndCriteria(Client client, TransactionDao transaction) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
        Root<Transaction> root = criteriaQuery.from(Transaction.class);

        if (transaction != null) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            predicates.add(criteriaBuilder.equal(root.get("client"), client));

            if (transaction.getReference() != null && !transaction.getReference().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("reference"), "%"+transaction.getReference()+"%"));
            }
            if (transaction.getTypeTransaction() != null) {
                predicates.add(criteriaBuilder.equal(root.get("typeTransaction"), transaction.getTypeTransaction()));
            }
            if (transaction.getTypeProduit() != null) {
                predicates.add(criteriaBuilder.equal(root.get("typeProduit"), transaction.getTypeProduit()));
            }
            if (transaction.getBeneficiaire_id() != null) {
                predicates.add(criteriaBuilder.equal(root.get("beneficiaire").get("id"), transaction.getBeneficiaire_id()));
            }
            if (transaction.getDateCreation() != null) {
                predicates.add(criteriaBuilder.like(root.get("dateCreation").as(String.class), transaction.getDateCreation().toString().substring(0,10) + "%"));
            }

            criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
            return entityManager.createQuery(criteriaQuery).getResultList();
        }
        return transactionRepository.findAllByClient(client);
    }
}
