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
public class ApDTOS {
    private int maAp;
    private int maXa;
    private String tenAp;
    private int huyen;
    private int tinh;
}
