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
public class XaDTOS {
    private int maXa;
    private String tenXa;
    private boolean isLoai;
    private int huyen;
    private int tinh;
}
