//Capacidad -> 4 Comprar un libro
pm.test("Retorno de solo propiedades requeridas id,nombre,valor", function () {

    pm.expect(pm.response.text()).to.include("libroId");
    pm.expect(pm.response.text()).to.include("libroName");
    pm.expect(pm.response.text()).to.include("valor");
});

pm.test("Validar todos los status", function () {
pm.expect(pm.response.code).to.be.oneOf([200,204,304,400,404,405,500]);
});

const jsonData = pm.response.json();
pm.test("Validar tipo de dato", () => {

  pm.expect(jsonData.libroName).to.be.a("string");
  pm.expect(jsonData.libroId).to.be.a("number");
  pm.expect(jsonData.valor).to.be.an("number");

});

pm.test("Tiempo de respuesta es menor a 1000ms", () => {
  pm.expect(pm.response.responseTime).to.be.below(1000);
});

pm.test("Si la peticion y respuesta contienen Content-Type ", () => {
  pm.request.to.have.header("Content-Type");
  pm.response.to.have.header("Content-Type");
});

pm.test("Validar formato del Content-Type de peticion y respuesta", () => {
  pm.expect(pm.request.headers.get('Content-Type')).to.eql('application/json');
  pm.expect(pm.response.headers.get('Content-Type')).to.eql('application/json');
});
