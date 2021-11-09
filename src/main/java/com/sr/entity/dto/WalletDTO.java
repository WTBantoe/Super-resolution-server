package com.sr.entity.dto;

import com.sr.entity.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author cyh
 * @Date 2021/11/9 16:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO {
    private Long balance;
    private Long totalIncome;
    private Long totalOutcome;

    public static WalletDTO convertWalletToWalletDTO(Wallet wallet) {
        WalletDTO walletDTO = new WalletDTO();
        walletDTO.setBalance(wallet.getBalance());
        walletDTO.setTotalIncome(wallet.getTotalIncome());
        walletDTO.setTotalOutcome(wallet.getTotalIoutcome());
        return walletDTO;
    }

    @Override
    public String toString() {
        return "WalletDTO{" +
                "balance=" + balance +
                ", totalIncome=" + totalIncome +
                ", totalOutcome=" + totalOutcome +
                '}';
    }
}
