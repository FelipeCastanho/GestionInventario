CREATE TABLE IF NOT EXISTS producto (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  nombre varchar(80) NOT NULL,
  cantidad int(12) UNSIGNED NOT NULL,
  costo int(20) UNSIGNED NOT NULL,
  estado varchar(20) NOT NULL
);
 
CREATE TABLE IF NOT EXISTS transaccion (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  nombreCliente varchar(80) NOT NULL,
  tipo varchar(20) NOT NULL,
  fecha DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS transaccion_producto (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  idProducto INT UNSIGNED NOT NULL,
  idTransaccion INT UNSIGNED NOT NULL,
  cantidad int(12) unsigned NOT NULL,
  valorUnitario int(20) unsigned NOT NULL,
  FOREIGN KEY (idProducto) REFERENCES producto(id),
  FOREIGN KEY (idTransaccion) REFERENCES transaccion(id)
);

CREATE TABLE IF NOT EXISTS devolucion (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  id_transaccion_producto INT UNSIGNED NOT NULL,
  nombreCliente varchar(80) NOT NULL,
  fecha DATETIME NOT NULL,
  FOREIGN KEY (id_transaccion_producto) REFERENCES transaccion_producto(id)
);



