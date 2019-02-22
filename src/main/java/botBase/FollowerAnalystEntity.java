package botBase;

import javax.persistence.*;

@Entity
@Table(name = "follower_analyst", schema = "public", catalog = "postgres")
@IdClass(FollowerAnalystEntityPK.class)
public class FollowerAnalystEntity {
    private int idCustomers;
    private int idAnalyst;
    private CustomersEntity customersByIdCustomers;

    @Id
    @Column(name = "id_customers", nullable = false, insertable = false,updatable = false)
    public int getIdCustomers() {
        return idCustomers;
    }

    public void setIdCustomers(int idCustomers) {
        this.idCustomers = idCustomers;
    }

    @Id
    @Column(name = "id_analyst", nullable = false)
    public int getIdAnalyst() {
        return idAnalyst;
    }

    public void setIdAnalyst(int idAnalyst) {
        this.idAnalyst = idAnalyst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowerAnalystEntity that = (FollowerAnalystEntity) o;

        if (idCustomers != that.idCustomers) return false;
        if (idAnalyst != that.idAnalyst) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCustomers;
        result = 31 * result + idAnalyst;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_customers", referencedColumnName = "id", nullable = false,  insertable = false,updatable = false)
    public CustomersEntity getCustomersByIdCustomers() {
        return customersByIdCustomers;
    }

    public void setCustomersByIdCustomers(CustomersEntity customersByIdCustomers) {
        this.customersByIdCustomers = customersByIdCustomers;
    }
}
