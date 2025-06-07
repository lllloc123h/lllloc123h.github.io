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
public class TinhDTOS {
    private int maTinh;
    private String tenTinh;
}
