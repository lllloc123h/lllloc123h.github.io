package com.ThongKe.ChanNuoi.DTOS;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoaiVanBanDTOS {
    private int maLoaiVanBan;
    private String tenMaLoaiVanBan;
    private String ghiChu;
}
