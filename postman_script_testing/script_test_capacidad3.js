//Capacidad -> 3 Crear un nuevo libro
pm.test("Validar todos los status", function () {
pm.expect(pm.response.code).to.be.oneOf([200,204,400,404,405,409,500]);
});

pm.test("Tiempo de respuesta es menor a 1000ms", () => {
  pm.expect(pm.response.responseTime).to.be.below(1000);
});

pm.test("Si la peticion contienen Content-Type ", () => {
  pm.request.to.have.header("Content-Type");
});

pm.test("Validar formato del Content-Type de la peticion", () => {
  pm.expect(pm.request.headers.get('Content-Type')).to.eql('application/json');
});



